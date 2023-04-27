package io.github.gunkim.data

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.game.Gamers
import io.github.gunkim.domain.room.Rooms
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DummyGamers(
    private val rooms: Rooms,
) : Gamers {
    override fun find() = rooms.find().flatMap { it.gamers }

    override fun find(id: UUID) = find().find { it.id == id } ?: throw IllegalArgumentException("존재하지 않는 플레이어입니다.")

    override fun findByUserId(userId: UUID) = find().find { it.user.id == userId } ?: throw IllegalArgumentException("존재하지 않는 플레이어입니다.")

    override fun save(gamer: Gamer) = gamer.also {
        val room = rooms.find().first { it.hasUserId(gamer.user.id) }
        val gamers = room.gamers.toMutableList()
        gamers.removeIf { it.id == gamer.id }
        gamers.add(gamer)
        rooms.save(room.copy(gamers = gamers))
    }
}
