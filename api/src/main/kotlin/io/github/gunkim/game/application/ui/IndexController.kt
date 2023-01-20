package io.github.gunkim.game.application.ui

import io.github.gunkim.game.application.usecase.room.JoinUseCase
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Controller
class IndexController(
    private val joinUseCase: JoinUseCase,
) {
    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/rooms/{roomId}/details")
    fun room(
        @PathVariable roomId: UUID,
        principal: OAuth2AuthenticationToken,
        model: Model,
    ): String {
        val userId = principal.principal.attributes["id"] as UUID

        joinUseCase.join(roomId, userId)

        return "room"
    }
}
