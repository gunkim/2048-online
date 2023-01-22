package io.github.gunkim.game.application.endpoint.socket.room

import io.github.gunkim.game.application.common.SessionUserId
import io.github.gunkim.game.application.endpoint.socket.room.request.MoveBoardRequest
import io.github.gunkim.game.application.usecase.board.MoveUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

interface MoveBoardController {
    @MessageMapping("/rooms/{roomId}/move")
    fun move(
        @SessionUserId sessionUserId: UUID,
        @DestinationVariable roomId: UUID,
        @RequestBody request: MoveBoardRequest,
    )
}

@RestController
class MoveBoardControllerImpl(
    private val moveUseCase: MoveUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) : MoveBoardController {
    override fun move(sessionUserId: UUID, roomId: UUID, request: MoveBoardRequest) {
        moveUseCase.move(roomId, sessionUserId, request.direction)

        messagingTemplate.convertAndSend(
            "/topic/room/$roomId",
            findUseCase.find(sessionUserId, roomId)
        )
    }
}
