package io.github.gunkim.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService

@Configuration
class Oauth2ServiceConfig {
    @Bean
    fun oauth2UserService() = DefaultOAuth2UserService()
}
