package io.github.gunkim.endpoint.http.room.response

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.room.Room
import java.util.*

data class WaitRoomResponse(
    val id: UUID,
    val title: String,
    val players: List<PlayerResponse>,
) {
    constructor(room: Room) : this(
        room.id,
        room.title,
        room.gamers
            .sortedBy(Gamer::order)
            .map(::PlayerResponse),
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
