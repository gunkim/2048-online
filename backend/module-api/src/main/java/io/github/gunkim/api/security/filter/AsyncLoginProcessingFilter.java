package io.github.gunkim.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gunkim.api.security.exception.AuthMethodNotSupportedException;
import io.github.gunkim.api.security.model.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AsyncLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;

    public AsyncLoginProcessingFilter(String defaultFilterProcessesUrl, ObjectMapper objectMapper, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        super(defaultFilterProcessesUrl);
        this.objectMapper = objectMapper;
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!HttpMethod.POST.name().equals(request.getMethod()) || this.isAsync(request)){
            log.debug("비동기 로그인 처리 지원이 되지 않는 메소드 요청입니다. :: "+request.getMethod());
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }
        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }
    private boolean isAsync(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}