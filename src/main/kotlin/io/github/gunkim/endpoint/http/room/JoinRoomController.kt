package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.FindRoom
import io.github.gunkim.application.JoinRoom
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.response.WaitRoomResponse
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class JoinRoomController(
    private val joinRoom: JoinRoom,
    private val findRoom: FindRoom,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PostMapping("/rooms/{roomId}/join")
    fun join(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) {
        joinRoom.join(roomId, user.id)

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId/wait",
            WaitRoomResponse(findRoom.find(user.id, roomId)),
        )
    }
}
