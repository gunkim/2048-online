package dev.gunlog.api.room;

import dev.gunlog.RestDocControllerTest;
import dev.gunlog.api.room.domain.enums.Mode;
import dev.gunlog.api.room.domain.enums.Personnel;
import dev.gunlog.api.room.dto.RoomListResponseDto;
import dev.gunlog.api.room.service.RoomService;
import dev.gunlog.api.security.service.CustomUserDetailsService;
import dev.gunlog.multi.domain.GameRoomRepository;
import dev.gunlog.multi.domain.UserRoomRepository;
import dev.gunlog.multi.model.GameRoom;
import dev.gunlog.multi.service.MultiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RoomController.class)
public class GameRoomControllerTests extends RestDocControllerTest {
    @MockBean
    private RoomService roomService;
    @MockBean
    private MultiService multiService;
    @MockBean
    private GameRoomRepository gameRoomRepository;
    @MockBean
    private UserRoomRepository userRoomRepository;
    @MockBean
    private SimpMessageSendingOperations simpMessageSendingOperations;

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
        when(multiService.findRoomByRoomId(any())).thenReturn(GameRoom.builder().build());

        mockMvc.perform(get("/api/v2/room/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].title").value("테스트 방1"))
                .andExpect(jsonPath("$.data[0].username").value("gunkim"))
                .andExpect(jsonPath("$.data[0].mode").value(Mode.SPEED_ATTACK.getTitle()))
                .andExpect(jsonPath("$.data[0].personnel").value(Personnel.FOUR.getSize()))
                .andExpect(jsonPath("$.data[0].participant").value(0))
                .andDo(document("room/rooms",
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("방 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("방 제목"),
                                fieldWithPath("username").type(JsonFieldType.STRING).description("방 호스트명"),
                                fieldWithPath("mode").type(JsonFieldType.STRING).description("방 게임 모드"),
                                fieldWithPath("personnel").type(JsonFieldType.NUMBER).description("방 최대 인원 수"),
                                fieldWithPath("participant").type(JsonFieldType.NUMBER).description("방 현재 인원 수")
                        )
                ));
    }
    @Test
    @DisplayName("방 생성 테스트")
    @WithMockUser(username = "gunkim")
    public void createRoomTest() throws Exception {
        when(roomService.createRoom(any(), any())).thenReturn(1L);

        String content = "{\"mode\":\"SPEED_ATTACK\",\"personnel\":\"FOUR\",\"title\":\"test\"}";
        mockMvc.perform(post("/api/v2/room")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roomId").value(1))
                .andDo(document("room/createRoom",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("방 이름"),
                                fieldWithPath("personnel").type(JsonFieldType.STRING).description("방 인원"),
                                fieldWithPath("mode").type(JsonFieldType.STRING).description("방 게임 모드")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("새로 생성된 방 ID")
                        )
                ));
    }
}