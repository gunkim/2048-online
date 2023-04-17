package io.github.gunkim.game.application.endpoint.http.room

import io.github.gunkim.game.application.common.id
import io.github.gunkim.game.application.endpoint.http.room.response.WaitRoomResponse
import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.application.usecase.room.ReadyUseCase
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ReadyRoomController(
    private val readyUseCase: ReadyUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PutMapping("/rooms/{roomId}/ready")
    fun ready(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) {
        readyUseCase.ready(user.id, roomId)

        messagingTemplate.convertAndSend(
            "/topic/rooms/${roomId}/wait",
            WaitRoomResponse(findUseCase.find(roomId))
        )
    }
}
