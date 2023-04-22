package io.github.gunkim.application.endpoint.socket.room

import io.github.gunkim.application.common.id
import io.github.gunkim.application.usecase.room.FindUseCase
import io.github.gunkim.application.usecase.room.StartUseCase
import io.github.gunkim.domain.Room
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class StartRoomController(
    private val startUseCase: StartUseCase,
    private val findUseCase: FindUseCase,
) {
    @MessageMapping("/rooms/{roomId}/start")
    @SendTo("/topic/room/{roomId}")
    fun start(
        user: OAuth2AuthenticationToken,
        @DestinationVariable roomId: UUID
    ): Room {
        startUseCase.start(roomId, user.id)

        return findUseCase.find(user.id, roomId)
    }
}
