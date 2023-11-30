package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.RoomService
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.request.CreateRoomRequest
import io.github.gunkim.endpoint.http.room.request.KickGamerRequest
import io.github.gunkim.endpoint.http.room.response.RoomResponse
import io.github.gunkim.endpoint.http.room.response.WaitRoomResponse
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        user: OAuth2AuthenticationToken,
        @RequestBody request: CreateRoomRequest
    ): UUID {
        val createdRoomId = roomService.create(request.title, user.id, request.timer).id
        val response = roomService.find().map(::RoomResponse)

        messagingTemplate.convertAndSend("/topic/rooms", response)

        return createdRoomId
    }

    @PostMapping("/{roomId}/join")
    fun join(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) {
        roomService.join(roomId, user.id)

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId/wait",
            WaitRoomResponse(roomService.find(user.id, roomId))
        )
    }

    @DeleteMapping("/{roomId}/leave")
    fun exit(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        if (roomService.leave(roomId, user.id)) {
            messagingTemplate.convertAndSend(
                "/topic/rooms/$roomId/wait",
                WaitRoomResponse(roomService.find(roomId))
            )
        } else {
            messagingTemplate.convertAndSend(
                "/topic/rooms",
                roomService.find().map(::RoomResponse)
            )
        }

    @PutMapping("/{roomId}/start")
    fun start(
        user: OAuth2AuthenticationToken,
        @PathVariable roomId: UUID
    ) {
        val room = roomService.start(roomId, user.id)

        messagingTemplate.convertAndSend("/topic/rooms/$roomId/start", room.playTime)
    }

    @PutMapping("/{roomId}/ready")
    fun ready(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) {
        roomService.ready(user.id, roomId)

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId/wait",
            WaitRoomResponse(roomService.find(roomId))
        )
    }

    @PutMapping("/{roomId}/kick")
    fun kick(
        user: OAuth2AuthenticationToken,
        @PathVariable roomId: UUID,
        @RequestBody request: KickGamerRequest
    ) {
        roomService.kick(roomId, user.id, request.gamerId)

        messagingTemplate.convertAndSend(
            "/topic/rooms/$roomId/wait",
            WaitRoomResponse(roomService.find(roomId))
        )
        messagingTemplate.convertAndSend("/topic/rooms/$roomId/kick", "Kick!")
    }
}
