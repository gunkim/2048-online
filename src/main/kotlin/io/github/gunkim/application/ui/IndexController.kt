package io.github.gunkim.application.ui

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping("/")
    fun index() = "index"

    @GetMapping("/rooms/{roomId}/details")
    fun room(user: OAuth2AuthenticationToken) = "room"

    @GetMapping("/waitroom/{roomId}")
    fun wait() = "waitroom"
}
