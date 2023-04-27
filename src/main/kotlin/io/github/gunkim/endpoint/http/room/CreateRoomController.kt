package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.CreateRoom
import io.github.gunkim.application.FindRoom
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.request.CreateRoomRequest
import io.github.gunkim.endpoint.http.room.response.RoomResponse
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class CreateRoomController(
    private val createRoom: CreateRoom,
    private val findRoom: FindRoom,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PostMapping("/rooms")
    fun create(
        user: OAuth2AuthenticationToken,
        @RequestBody request: CreateRoomRequest,
    ): ResponseEntity<URI> {
        val createdRoomId = createRoom.create(request.title, user.id).id
        val response = findRoom.find().map(::RoomResponse)

        messagingTemplate.convertAndSend("/topic/rooms", response)

        return ResponseEntity.created(URI.create("/waitroom/$createdRoomId")).build()
    }
}
