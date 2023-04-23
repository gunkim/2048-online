package io.github.gunkim.application.spring.endpoint.socket.room

import io.github.gunkim.application.spring.common.id
import io.github.gunkim.application.spring.endpoint.socket.room.request.MoveBoardRequest
import io.github.gunkim.application.MoveBoard
import io.github.gunkim.application.FindRoom
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class MoveBoardController(
    private val moveBoard: MoveBoard,
    private val findRoom: FindRoom,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @MessageMapping("/rooms/{roomId}/move")
    fun move(
        user: OAuth2AuthenticationToken,
        @DestinationVariable roomId: UUID,
        @RequestBody request: MoveBoardRequest,
    ) {
        moveBoard.move(roomId, user.id, request.direction)

        messagingTemplate.convertAndSend(
            "/topic/room/$roomId",
            findRoom.find(user.id, roomId)
        )
    }
}
