package io.github.gunkim.application.endpoint.http.room

import io.github.gunkim.application.common.id
import io.github.gunkim.application.endpoint.http.room.response.WaitRoomResponse
import io.github.gunkim.application.usecase.room.FindUseCase
import io.github.gunkim.application.usecase.room.JoinUseCase
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class JoinRoomController(
    private val joinUseCase: JoinUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PostMapping("/rooms/{roomId}/join")
    fun join(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) {
        joinUseCase.join(roomId, user.id)

        messagingTemplate.convertAndSend(
            "/topic/rooms/${roomId}/wait",
            WaitRoomResponse(findUseCase.find(user.id, roomId))
        )
    }
}
