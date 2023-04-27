package io.github.gunkim.config

import io.github.gunkim.application.service.OAuth2Service
import io.github.gunkim.domain.Role
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.web.SecurityFilterChain

@Configuration
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class WebSecurityConfig(
    private val oAuth2Service: OAuth2Service,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
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
                "/img/**",
                "/rooms",
            ).permitAll()
            .requestMatchers(
                "/rooms/*/details",
                "/rooms/*",
                "/waitroom/*",
                "/rooms/*/wait",
                "/rooms/*/join",
                "/rooms/*/leave",
                "/rooms/*/ready",
            )
            .hasRole(Role.USER.name)
            .anyRequest().denyAll().and()
            .oauth2Login().userInfoEndpoint().userService(oAuth2Service)
            .let { http.build() }

    @Bean
    fun oauth2UserService() = DefaultOAuth2UserService()
}
