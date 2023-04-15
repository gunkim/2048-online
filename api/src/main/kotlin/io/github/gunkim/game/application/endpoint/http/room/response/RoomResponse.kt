package io.github.gunkim.game.application.endpoint.http.room.response

import io.github.gunkim.game.domain.Room
import java.util.UUID

data class RoomResponse(
    val id: UUID,
    val title: String,
    val host: String,
    val isStart: Boolean,
) {
    constructor(room: Room) : this(
        room.id,
        room.title,
        room.hostName,
        room.isStart,
    )
}
