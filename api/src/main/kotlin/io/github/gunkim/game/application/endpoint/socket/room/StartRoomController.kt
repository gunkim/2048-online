package io.github.gunkim.game.application.endpoint.socket.room

import io.github.gunkim.game.application.common.SessionUserId
import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.application.usecase.room.StartUseCase
import io.github.gunkim.game.domain.Room
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

interface StartRoomController {
    @MessageMapping("/rooms/{roomId}/start")
    @SendTo("/topic/room/{roomId}")
    fun start(
        @SessionUserId sessionUserId: UUID,
        @DestinationVariable roomId: UUID,
    ): Room
}

@RestController
class StartRoomControllerImpl(
    private val startUseCase: StartUseCase,
    private val findUseCase: FindUseCase,
) : StartRoomController {
    override fun start(sessionUserId: UUID, roomId: UUID): Room {
        startUseCase.start(roomId, sessionUserId)

        return findUseCase.find(sessionUserId, roomId)
    }
}
