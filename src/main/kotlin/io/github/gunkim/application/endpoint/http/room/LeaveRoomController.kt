package io.github.gunkim.application.endpoint.http.room

import io.github.gunkim.application.common.id
import io.github.gunkim.application.endpoint.http.room.response.RoomResponse
import io.github.gunkim.application.endpoint.http.room.response.WaitRoomResponse
import io.github.gunkim.application.usecase.room.FindUseCase
import io.github.gunkim.application.usecase.room.LeaveUseCase
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class LeaveRoomController(
    private val leaveUseCase: LeaveUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @DeleteMapping("/rooms/{roomId}/leave")
    fun exit(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        if (leaveUseCase.leave(roomId, user.id)) {
            messagingTemplate.convertAndSend(
                "/topic/rooms/${roomId}/wait",
                WaitRoomResponse(findUseCase.find(roomId))
            )
        } else {
            messagingTemplate.convertAndSend(
                "/topic/rooms",
                findUseCase.find().map(::RoomResponse)
            )
        }
}
