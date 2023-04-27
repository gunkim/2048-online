package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.FindRoom
import io.github.gunkim.application.ReadyRoom
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.response.WaitRoomResponse
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ReadyRoomController(
    private val readyRoom: ReadyRoom,
    private val findRoom: FindRoom,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PutMapping("/rooms/{roomId}/ready")
    fun ready(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) {
        readyRoom.ready(user.id, roomId)

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId/wait",
            WaitRoomResponse(findRoom.find(roomId)),
        )
    }
}
