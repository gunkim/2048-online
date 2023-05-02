package io.github.gunkim.endpoint.socket.room

import io.github.gunkim.application.board.MoveBoard
import io.github.gunkim.application.room.FindRoom
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.response.GameResponse
import io.github.gunkim.endpoint.socket.room.request.MoveBoardRequest
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

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
            "/topic/rooms/$roomId/game",
            findRoom.find(user.id, roomId).gamers
                .sortedBy { it.order }
                .map(::GameResponse),
        )
    }
}
