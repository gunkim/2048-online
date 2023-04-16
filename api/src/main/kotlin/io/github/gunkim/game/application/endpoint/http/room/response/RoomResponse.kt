package io.github.gunkim.game.application.endpoint.http.room.response

import io.github.gunkim.game.domain.Room
import java.util.UUID

data class RoomResponse(
    val id: UUID,
    val title: String,
    val maxPlayer: Int,
    val currentPlayer: Int,
) {
    constructor(room: Room) : this(
        room.id,
        room.title,
        4, //TODO 이 부분은 도메인으로 추출이 필요할 듯?
        room.gamers.size
    )
}
