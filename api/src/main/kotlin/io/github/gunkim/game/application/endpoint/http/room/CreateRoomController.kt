package io.github.gunkim.game.application.endpoint.http.room

import io.github.gunkim.game.application.common.SessionUserId
import io.github.gunkim.game.application.endpoint.socket.room.request.CreateRoomRequest
import io.github.gunkim.game.application.endpoint.socket.room.response.RoomResponse
import io.github.gunkim.game.application.usecase.room.CreateUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

interface CreateRoomController {
    @PostMapping("/rooms")
    fun create(
        @SessionUserId sessionUserId: UUID,
        @RequestBody request: CreateRoomRequest,
    ): ResponseEntity<URI>
}

@RestController
class CreateRoomControllerImpl(
    private val createUseCase: CreateUseCase,
    private val findUseCase: FindUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) : CreateRoomController {
    override fun create(sessionUserId: UUID, request: CreateRoomRequest): ResponseEntity<URI> {
        val createdRoomId = createUseCase.create(request.title, sessionUserId).id
        val response = findUseCase.find().map(::RoomResponse)

        messagingTemplate.convertAndSend("/topic/rooms", response)

        return ResponseEntity.created(URI.create("/rooms/$createdRoomId/details")).build()
    }
}
