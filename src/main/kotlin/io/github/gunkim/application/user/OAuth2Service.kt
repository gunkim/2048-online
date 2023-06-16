package io.github.gunkim.application.user

import io.github.gunkim.domain.user.Role
import io.github.gunkim.domain.user.Social
import io.github.gunkim.domain.user.User
import io.github.gunkim.domain.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

val OAuth2UserRequest.userNameAttributeName: String
    get() = clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

@Service
class OAuth2Service(
    private val userRepository: UserRepository,
    private val delegate: DefaultOAuth2UserService,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val (oAuth2User, userNameAttributeName) = loadOAuth2UserInfo(delegate, userRequest)
        val attributes = OAuthAttributes(userNameAttributeName, oAuth2User)

        return getUser(attributes).run {
            DefaultOAuth2User(
                listOf(SimpleGrantedAuthority("ROLE_${role.name}")),
                attributes.attributes + mapOf("id" to id),
                attributes.nameAttributeKey,
            )
        }
    }

    private fun getUser(attributes: OAuthAttributes) = userRepository.save(
        userRepository.findByEmail(attributes.email)
            ?.apply { update(attributes.name, attributes.picture) }
            ?: attributes.toUser(),
    )

    private fun loadOAuth2UserInfo(
        delegate: DefaultOAuth2UserService,
        userRequest: OAuth2UserRequest,
    ): Pair<Map<String, Any>, String> {
        val oAuth2User = delegate.loadUser(userRequest)
        val userNameAttributeName = userRequest.userNameAttributeName

        return oAuth2User.attributes to userNameAttributeName
    }

    private class OAuthAttributes private constructor(
        val attributes: Map<String, Any>,
        val nameAttributeKey: String,
        val name: String,
        val email: String,
        val picture: String,
    ) {
        constructor(
            userNameAttributeName: String,
            attributes: Map<String, Any>,
        ) : this(
            attributes,
            userNameAttributeName,
            attributes["name"] as String,
            attributes["email"] as String,
            attributes["picture"] as String,
        )

        fun toUser() = User(
            name = name,
            email = email,
            profileImageUrl = picture,
            role = Role.USER,
            social = Social.GOOGLE,
        )
    }
}
