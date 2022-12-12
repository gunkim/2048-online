package Io.github.gunkim

import io.github.gunkim.game.domain.Gamer
import io.github.gunkim.game.domain.Gamers
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DummyGamers(
    private val map: MutableMap<UUID, Gamer> = mutableMapOf()
) : Gamers {
    override fun find() = map.values.toList()

    override fun find(id: UUID) = map[id] ?: throw IllegalArgumentException("존재하지 않는 유저입니다.")

    override fun findByUserId(userId: UUID) = map.values.find { it.user.id == userId }
        ?: throw IllegalArgumentException("존재하지 않는 유저입니다.")

    override fun save(gamer: Gamer) = gamer.also { map[gamer.id] = gamer }
}
