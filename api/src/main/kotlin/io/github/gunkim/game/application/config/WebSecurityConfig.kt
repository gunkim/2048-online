package io.github.gunkim.game.application.config

import io.github.gunkim.game.application.config.service.OAuth2Service
import io.github.gunkim.game.domain.Role
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
open class WebSecurityConfig(
    private val oAuth2Service: OAuth2Service
) {
    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf().disable()
            .cors().disable()
            .httpBasic().disable()
            .authorizeHttpRequests()
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
                "/rooms",
            ).permitAll()
            .requestMatchers("/rooms/*/details", "/rooms/*").hasRole(Role.USER.name)
            .anyRequest().denyAll().and()
            .oauth2Login().userInfoEndpoint().userService(oAuth2Service)
            .let { http.build() }
}
