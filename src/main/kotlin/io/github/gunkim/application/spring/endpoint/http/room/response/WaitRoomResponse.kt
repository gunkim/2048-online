package io.github.gunkim.application.spring.endpoint.http.room.response

import io.github.gunkim.domain.Gamer
import io.github.gunkim.domain.Room
import java.util.UUID

data class WaitRoomResponse(
    val id: UUID,
    val title: String,
    val players: List<PlayerResponse>,
) {
    constructor(room: Room) : this(
        room.id,
        room.title,
        room.gamers.map(::PlayerResponse),
    )
}

data class PlayerResponse(
    val id: UUID,
    val name: String,
    val profileImageUrl: String?,
    val isReady: Boolean,
    val isHost: Boolean,
) {
    constructor(gamer: Gamer) : this(
        gamer.user.id,
        gamer.user.name,
        gamer.user.profileImageUrl,
        gamer.isReady,
        gamer.isHost,
    )
}
