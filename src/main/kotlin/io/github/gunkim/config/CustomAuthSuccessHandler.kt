package io.github.gunkim.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class CustomAuthSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        response.apply {
            // TODO: 해당 redirect uri는 config로 관리될 필요가 있을 듯 함.
            setHeader(HttpHeaders.LOCATION, "http://localhost:5173")
            status = HttpStatus.FOUND.value()
        }
    }
}
