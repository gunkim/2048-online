package io.github.gunkim.game.application.endpoint.http.room

import io.github.gunkim.game.application.endpoint.socket.room.FindRoomResponse
import io.github.gunkim.game.application.usecase.room.CreateUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class CreateController(
    private val createUseCase: CreateUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @PostMapping("/rooms")
    fun create(
        principal: OAuth2AuthenticationToken,
        @RequestBody request: RoomCreateRequest,
    ): ResponseEntity<URI> {
        val userId = principal.principal.attributes["id"] as UUID
        val createdRoomId = createUseCase.create(request.title, userId).id

        val response = findUseCase.find()
            .map(::FindRoomResponse)
        messagingTemplate.convertAndSend("/topic/rooms", response)

        return ResponseEntity.created(URI.create("/rooms/$createdRoomId/details")).build()
    }
}

data class RoomCreateRequest(val title: String = "")
