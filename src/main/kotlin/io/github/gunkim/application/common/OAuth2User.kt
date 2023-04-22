package io.github.gunkim.application.common

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import java.util.UUID

val OAuth2AuthenticationToken.id get() = principal.attributes["id"] as UUID
