package io.github.gunkim.game.application.socket.room

import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.domain.Room
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class FindController(
    private val findUseCase: FindUseCase,
)

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
