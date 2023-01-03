package io.github.gunkim.game.application.ui

import io.github.gunkim.game.application.socket.room.FindRoomResponse
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Controller
class IndexController(
    private val findRoomUseCase: FindUseCase
) {
    @GetMapping("/")
    fun index(): String {
        return "index"
    }
    @GetMapping("/rooms/{roomId}/details")
    fun room(
        @PathVariable roomId: UUID,
        principal: OAuth2AuthenticationToken,
        model: Model
    ): String {
        val userId = principal.principal.attributes["id"] as UUID

        model.addAttribute("room", findRoomUseCase.find(userId, roomId))

        return "room"
    }

    private fun roomResponses() = findRoomUseCase.find()
        .map(::FindRoomResponse)
}
