package dev.gunlog.api.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunlog.RestDocControllerTest;
import dev.gunlog.api.security.filter.AsyncLoginProcessingFilter;
import dev.gunlog.api.security.handler.AsyncLoginAuthenticationSuccessHandler;
import dev.gunlog.api.security.handler.CommonAuthenticationFailureHandler;
import dev.gunlog.api.security.provider.AsyncAuthenticationProvider;
import dev.gunlog.api.security.provider.JwtAuthenticationProvider;
import dev.gunlog.api.security.service.CustomUserDetailsService;
import dev.gunlog.common.ApiResponse;
import dev.gunlog.config.SecurityConfig;
import dev.gunlog.api.member.domain.Member;
import dev.gunlog.api.member.domain.MemberRepository;
import dev.gunlog.api.member.domain.Role;
import dev.gunlog.api.security.model.LoginRequest;
import dev.gunlog.api.security.util.JwtUtil;
import dev.gunlog.config.TestSecurityConfig;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
                        .name("TEST USER")
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
                .andReturn();

        ApiResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResponse.class);
        final String jwtToken = response.getData().replaceAll("\"", "");

        final Claims body = jwtUtil.parserToken(jwtToken).getBody();
        assertThat(body.getSubject()).isEqualTo(MEMBER_ID);
        assertThat(body.getIssuer()).isEqualTo(issuer);
    }
    private String getLoginInfo() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(LoginRequest.builder()
                .username(MEMBER_ID)
                .password(PASSWORD)
                .build());
    }
}
