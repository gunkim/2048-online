package io.github.gunkim.api.security.filter;

import io.github.gunkim.api.security.JwtAuthenticationToken;
import io.github.gunkim.config.SecurityConfig;
import io.github.gunkim.api.security.util.JwtUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtUtil jwtUtil;

    public JwtTokenAuthenticationProcessingFilter(RequestMatcher matcher, AuthenticationFailureHandler failureHandler, JwtUtil jwtUtil) {
        super(matcher);
        this.setAuthenticationFailureHandler(failureHandler);
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String tokenPayload = request.getHeader(SecurityConfig.AUTHENTICATION_HEADER_NAME);

        Jws<Claims> claimsJws = jwtUtil.parserToken(tokenPayload);

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(claimsJws));
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}