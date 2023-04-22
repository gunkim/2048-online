package io.github.gunkim.application.config.service

import io.github.gunkim.domain.Role
import io.github.gunkim.domain.User
import io.github.gunkim.domain.Users
import io.github.gunkim.domain.vo.Social
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2Service(
    private val users: Users,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()

        val (oAuth2User, userNameAttributeName) = initData(delegate, userRequest)
        val attributes = OAuthAttributes.ofGoogle(userNameAttributeName, oAuth2User)
        val user = getUser(attributes)

        return DefaultOAuth2User(
            listOf(SimpleGrantedAuthority("ROLE_${user.role.name}")),
            attributes.attributes + mapOf("id" to user.id),
            attributes.nameAttributeKey,
        )
    }

    private fun getUser(attributes: OAuthAttributes): User {
        val user = users.findByEmail(attributes.email)
            ?.apply { update(attributes.name, attributes.picture) }
            ?: attributes.toUser()
        return users.save(user)
    }

    private fun initData(
        delegate: DefaultOAuth2UserService,
        userRequest: OAuth2UserRequest,
    ): Pair<Map<String, Any>, String> {
        val oAuth2User = delegate.loadUser(userRequest)
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        return oAuth2User.attributes to userNameAttributeName
    }
}

class OAuthAttributes(
    val attributes: Map<String, Any>,
    val nameAttributeKey: String,
    val name: String,
    val email: String,
    val picture: String,
) {
    fun toUser(): User {
        return User(
            name = name,
            email = email,
            profileImageUrl = picture,
            role = Role.USER,
            social = Social.GOOGLE,
        )
    }

    companion object {
        fun ofGoogle(
            userNameAttributeName: String,
            attributes: Map<String, Any>,
        ): OAuthAttributes {
            return OAuthAttributes(
                attributes,
                userNameAttributeName,
                attributes["name"] as String,
                attributes["email"] as String,
                attributes["picture"] as String,
            )
        }
    }
}
