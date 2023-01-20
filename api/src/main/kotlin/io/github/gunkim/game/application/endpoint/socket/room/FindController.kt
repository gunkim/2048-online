package io.github.gunkim.game.application.endpoint.socket.room

import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.domain.Room
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class FindController(
    private val findUseCase: FindUseCase,
) {
    @GetMapping("/rooms")
    fun find() = findUseCase.find()
        .map(::FindRoomResponse)

    @GetMapping("/rooms/{roomId}")
    fun findDetails(
        principal: OAuth2AuthenticationToken,
        @PathVariable roomId: UUID
    ): Room {
        val userId = principal.principal.attributes["id"] as UUID
        return findUseCase.find(userId, roomId)
    }
}

data class FindRoomResponse(
    val id: UUID,
    val title: String,
    val host: String,
    val isStart: Boolean
) {
    constructor(room: Room) : this(
        room.id,
        room.title,
        room.hostName,
        room.isStart
    )
}
