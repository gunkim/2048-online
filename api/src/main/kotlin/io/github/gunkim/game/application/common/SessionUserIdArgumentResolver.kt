package io.github.gunkim.game.application.common

import org.springframework.core.MethodParameter
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.UUID

@Component
class SessionUserIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val isSessionUserIdAnnotation = parameter.getParameterAnnotation(SessionUserId::class.java) != null
        val isUUIDType = parameter.parameterType == UUID::class.java

        return isSessionUserIdAnnotation && isUUIDType
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): UUID {
        val token = webRequest.userPrincipal as OAuth2AuthenticationToken

        return token.principal.attributes["id"] as UUID
    }

}
