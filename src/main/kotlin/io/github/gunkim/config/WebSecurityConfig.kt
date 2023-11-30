package io.github.gunkim.config

import io.github.gunkim.application.OAuth2Service
import io.github.gunkim.domain.user.Role
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class WebSecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity, oAuth2Service: OAuth2Service): SecurityFilterChain {
        http.csrf().disable()
            .cors().disable()

        http.authorizeHttpRequests()
            .requestMatchers(
                "/websocket",
                "/",
                "/favicon.ico",
                "/error",
                "/websocket/info",
                "/websocket/*/*/*",
                "/websocket/*/*",
                "/js/**",
                "/css/**",
                "/img/**",
                "/rooms",
                "/game/**"
            ).permitAll()
            .requestMatchers(
                "/rooms/*/details",
                "/rooms/*",
                "/waitroom/*",
                "/rooms/*/wait",
                "/rooms/*/join",
                "/rooms/*/leave",
                "/rooms/*/ready",
                "/rooms/*/game",
                "/rooms/*/games",
                "/rooms/*/start"
            )
            .hasRole(Role.USER.name)
            .anyRequest().authenticated()

        http.oauth2Login()
            .successHandler(CustomAuthSuccessHandler())
            .userInfoEndpoint()
            .userService(oAuth2Service)

        return http.build()
    }
}
