package io.github.gunkim.game.application.socket.room

import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.application.usecase.room.StartUseCase
import io.github.gunkim.game.domain.Room
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class StartRoomController(
    private val startUseCase: StartUseCase,
    private val findUseCase: FindUseCase
) {
    @MessageMapping("/room/{roomId}/start")
    @SendTo("/topic/room/{roomId}")
    fun start(
        principal: OAuth2AuthenticationToken,
        @DestinationVariable roomId: UUID
    ): Room {
        val userId = principal.principal.attributes["id"] as UUID
        startUseCase.start(roomId, userId)

        return findUseCase.find(userId, roomId)
    }
}
