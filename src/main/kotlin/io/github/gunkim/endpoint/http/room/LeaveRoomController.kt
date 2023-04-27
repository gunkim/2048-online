package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.room.FindRoom
import io.github.gunkim.application.room.LeaveRoom
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.response.RoomResponse
import io.github.gunkim.endpoint.http.room.response.WaitRoomResponse
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class LeaveRoomController(
    private val leaveRoom: LeaveRoom,
    private val findRoom: FindRoom,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @DeleteMapping("/rooms/{roomId}/leave")
    fun exit(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        if (leaveRoom.leave(roomId, user.id)) {
            messagingTemplate.convertAndSend(
                "/topic/rooms/$roomId/wait",
                WaitRoomResponse(findRoom.find(roomId)),
            )
        } else {
            messagingTemplate.convertAndSend(
                "/topic/rooms",
                findRoom.find().map(::RoomResponse),
            )
        }
}
