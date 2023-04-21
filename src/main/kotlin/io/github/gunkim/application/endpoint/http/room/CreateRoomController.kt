package io.github.gunkim.game.application.endpoint.http.room

import io.github.gunkim.game.application.common.id
import io.github.gunkim.game.application.endpoint.http.room.request.CreateRoomRequest
import io.github.gunkim.game.application.endpoint.http.room.response.RoomResponse
import io.github.gunkim.game.application.usecase.room.CreateUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class CreateRoomController(
    private val createUseCase: CreateUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PostMapping("/rooms")
    fun create(
        user: OAuth2AuthenticationToken,
        @RequestBody request: CreateRoomRequest
    ): ResponseEntity<URI> {
        val createdRoomId = createUseCase.create(request.title, user.id).id
        val response = findUseCase.find().map(::RoomResponse)

        messagingTemplate.convertAndSend("/topic/rooms", response)

        return ResponseEntity.created(URI.create("/waitroom/$createdRoomId")).build()
    }
}
