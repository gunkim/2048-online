package dev.gunlog.api.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunlog.config.SecurityConfig;
import dev.gunlog.enums.Mode;
import dev.gunlog.enums.Personnel;
import dev.gunlog.api.member.domain.Role;
import dev.gunlog.api.security.model.LoginRequest;
import dev.gunlog.api.room.dto.RoomListResponseDto;
import dev.gunlog.api.room.service.RoomService;
import dev.gunlog.api.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameRoomControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoomService roomService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        final String memberId = "gunkim";
        given(customUserDetailsService.loadUserByUsername(memberId))
                .willReturn(new User(memberId, "$2a$10$jHc8ndvWho7p/a2/kVvfJOVgYiC1rLy9nT2ddRjUfzulh4/6vYXyC",
                        List.of(new SimpleGrantedAuthority(Role.USER.name()))));

        final LoginRequest loginRequest = new LoginRequest(memberId, "gunkim");
        final String content = objectMapper.writeValueAsString(loginRequest);
        MvcResult result = mockMvc.perform(post("/api/v2/member/signIn")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        this.token = result.getResponse().getContentAsString().replaceAll("\"", "");
    }
    @Test
    @DisplayName("방 목록 조회 테스트")
    public void roomsTest() throws Exception {
        when(roomService.getAllRooms()).thenReturn(List.of(
                RoomListResponseDto.builder()
                        .id(1L)
                        .title("테스트 방1")
                        .mode(Mode.SPEED_ATTACK)
                        .username("gunkim")
                        .personnel(Personnel.FOUR)
                        .build()
        ));

        mockMvc.perform(get("/api/v2/room/list")
                .header(SecurityConfig.AUTHENTICATION_HEADER_NAME, this.token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].mode").value(Mode.SPEED_ATTACK.getTitle()))
                .andExpect(jsonPath("$[0].title").value("테스트 방1"))
                .andExpect(jsonPath("$[0].personnel").value(Personnel.FOUR.getSize()))
                .andExpect(jsonPath("$[0].username").value("gunkim"));
    }
    @Test
    @DisplayName("방 생성 테스트")
    public void createRoomTest() throws Exception {
        when(roomService.createRoom(any(), any())).thenReturn(1L);

        String content = "{\"mode\":\"SPEED_ATTACK\",\"personnel\":\"FOUR\",\"title\":\"test\"}";
        mockMvc.perform(post("/api/v2/room")
                .header(SecurityConfig.AUTHENTICATION_HEADER_NAME, this.token)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk());
    }
}