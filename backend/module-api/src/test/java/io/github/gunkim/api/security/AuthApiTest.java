package io.github.gunkim.api.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gunkim.RestDocControllerTest;
import io.github.gunkim.api.security.handler.AsyncLoginAuthenticationSuccessHandler;
import io.github.gunkim.api.security.handler.CommonAuthenticationFailureHandler;
import io.github.gunkim.api.security.model.LoginResponseDto;
import io.github.gunkim.api.security.provider.AsyncAuthenticationProvider;
import io.github.gunkim.api.security.provider.JwtAuthenticationProvider;
import io.github.gunkim.api.security.service.CustomUserDetailsService;
import io.github.gunkim.common.ApiResponse;
import io.github.gunkim.config.SecurityConfig;
import io.github.gunkim.member.domain.Member;
import io.github.gunkim.member.domain.MemberRepository;
import io.github.gunkim.member.domain.Role;
import io.github.gunkim.api.security.model.LoginRequestDto;
import io.github.gunkim.api.security.util.JwtUtil;
import io.github.gunkim.config.TestSecurityConfig;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(exclude = {
        TestSecurityConfig.class,
        FilterChainProxy.class
})
@ContextConfiguration(classes = {
        SecurityConfig.class,
        BCryptPasswordEncoder.class,
        AsyncLoginAuthenticationSuccessHandler.class,
        CommonAuthenticationFailureHandler.class,
        JwtUtil.class,
        AsyncAuthenticationProvider.class,
        CustomUserDetailsService.class,
        JwtAuthenticationProvider.class
})
public class AuthApiTest extends RestDocControllerTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private MemberRepository memberRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public static final String MEMBER_ID = "testMan";
    public static final String PASSWORD = "test";

    @Value("${jwt.token.issuer}")
    private String issuer;

    @BeforeEach
    void createMember() {
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(Member.builder()
                        .memberId(MEMBER_ID)
                        .nickname("TEST USER")
                        .password(passwordEncoder.encode(PASSWORD))
                        .role(Role.USER)
                        .build()));
    }
    @Test
    @DisplayName("로그인 테스트")
    void signInTest() throws Exception {
        final MvcResult result = mockMvc.perform(post(SecurityConfig.AUTHENTICATION_URL)
                .content(getLoginInfo()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("auth",
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("유저 비밀번호")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 jwt 토큰")
                        )
                ))
                .andReturn();

        ApiResponse<LoginResponseDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ApiResponse<LoginResponseDto>>() {});
        final String jwtToken = response.getData().getAccessToken().replaceAll("\"", "");

        final Claims body = jwtUtil.parserToken(jwtToken).getBody();
        assertThat(body.getSubject()).isEqualTo(MEMBER_ID);
        assertThat(body.getIssuer()).isEqualTo(issuer);
    }
    private String getLoginInfo() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(LoginRequestDto.builder()
                .username(MEMBER_ID)
                .password(PASSWORD)
                .build());
    }
}
