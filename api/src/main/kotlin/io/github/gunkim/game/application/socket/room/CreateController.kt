package io.github.gunkim.game.application.socket.room

import io.github.gunkim.game.application.usecase.room.CreateUseCase
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CreateController(
    private val createUseCase: CreateUseCase,
    private val findUseCase: FindUseCase
) {
    @MessageMapping("/room/create")
    @SendTo("/topic/rooms")
    fun create(
        title: String
    ): List<FindRoomResponse> {
        val userId = userId!!
        createUseCase.create(title, userId)

        return findUseCase.find()
            .map(::FindRoomResponse)
    }

    companion object {
        var userId: UUID? = null
    }
}

data class RoomCreateRequest(val title: String)
