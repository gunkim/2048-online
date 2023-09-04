package io.github.gunkim.data

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.game.GamerRepository
import io.github.gunkim.domain.room.RoomRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DummyGamerRepository(
    private val roomRepository: RoomRepository,
) : GamerRepository {
    override fun find() = roomRepository.find().flatMap { it.gamers }

    override fun find(id: UUID) = find().find { it.hasPlayerId(id) } ?: throw IllegalArgumentException("존재하지 않는 플레이어입니다.")

    override fun findByUserId(userId: UUID) = find().find { it.user.id == userId } ?: throw IllegalArgumentException("존재하지 않는 플레이어입니다.")

    override fun save(gamer: Gamer) = gamer.also {
        val room = roomRepository.find().first { it.hasUserId(gamer.user.id) }
        val gamers = room.gamers.toMutableList()
        gamers.removeIf { it.user == gamer.user }
        gamers.add(gamer)
        roomRepository.save(room.copy(gamers = gamers))
    }
}
