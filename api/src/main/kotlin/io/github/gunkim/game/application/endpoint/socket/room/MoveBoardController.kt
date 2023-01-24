package io.github.gunkim.game.application.endpoint.socket.room

import io.github.gunkim.game.application.common.id
import io.github.gunkim.game.application.endpoint.socket.room.request.MoveBoardRequest
import io.github.gunkim.game.application.usecase.board.MoveUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class MoveBoardController(
    private val moveUseCase: MoveUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @MessageMapping("/rooms/{roomId}/move")
    fun move(
        user: OAuth2AuthenticationToken,
        @DestinationVariable roomId: UUID,
        @RequestBody request: MoveBoardRequest,
    ) {
        moveUseCase.move(roomId, user.id, request.direction)

        messagingTemplate.convertAndSend(
            "/topic/room/$roomId",
            findUseCase.find(user.id, roomId)
        )
    }
}
