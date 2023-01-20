package io.github.gunkim.game.application.endpoint.socket.room

import io.github.gunkim.game.application.usecase.board.MoveUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.domain.vo.MoveType
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MoveController(
    private val moveUseCase: MoveUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/rooms/{roomId}/move/{moveType}")
    fun move(
        principal: OAuth2AuthenticationToken,
        @DestinationVariable roomId: UUID,
        @DestinationVariable moveType: String
    ) {
        val userId = principal.principal.attributes["id"] as UUID
        val moveType = MoveType.valueOf(moveType.uppercase())

        moveUseCase.move(roomId, userId, moveType)

        messagingTemplate.convertAndSend("/topic/room/${roomId}", findUseCase.find(userId, roomId))
    }
}
