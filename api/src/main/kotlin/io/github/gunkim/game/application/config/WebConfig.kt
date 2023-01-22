package io.github.gunkim.game.application.config

import io.github.gunkim.game.application.common.SessionUserIdArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebConfig(
    private val sessionUserIdArgumentResolver: SessionUserIdArgumentResolver
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(sessionUserIdArgumentResolver)
    }
}
