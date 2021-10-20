package dev.gunlog.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunlog.common.ApiResponse;
import dev.gunlog.common.WebStatusCode;
import dev.gunlog.security.exception.AuthMethodNotSupportedException;
import dev.gunlog.security.exception.JwtExpiredTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CommonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse apiResponse = null;
        if (exception instanceof BadCredentialsException) {
            apiResponse = ApiResponse.of(WebStatusCode.INVALID_PASSWORD_VALUE);
        } else if (exception instanceof AuthMethodNotSupportedException) {
            apiResponse = ApiResponse.of(WebStatusCode.METHOD_NOT_ALLOWED);
        } else if(exception instanceof JwtExpiredTokenException){
            apiResponse = ApiResponse.of(WebStatusCode.JWT_EXPIRED_TOKEN);
        } else {
            apiResponse = ApiResponse.of(WebStatusCode.INVALID_AUTH_REQUEST);
        }
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
