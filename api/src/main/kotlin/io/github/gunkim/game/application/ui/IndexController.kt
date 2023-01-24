package io.github.gunkim.game.application.ui

import io.github.gunkim.game.application.common.id
import io.github.gunkim.game.application.usecase.room.JoinUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

interface IndexController {
    @GetMapping("/")
    fun index(): String

    @GetMapping("/rooms/{roomId}/details")
    fun room(
        user: OAuth2AuthenticationToken,
        @PathVariable roomId: UUID,
    ): String
}

@Controller
class IndexControllerImpl(
    private val joinUseCase: JoinUseCase,
) : IndexController {
    override fun index() = "index"

    override fun room(user: OAuth2AuthenticationToken, roomId: UUID): String {
//        joinUseCase.join(roomId, user.id)

        return "room"
    }
}
