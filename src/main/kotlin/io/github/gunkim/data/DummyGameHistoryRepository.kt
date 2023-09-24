package io.github.gunkim.data

import io.github.gunkim.domain.history.GameHistory
import io.github.gunkim.domain.history.GameHistoryRepository
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
class DummyGameHistoryRepository(
    private val dummy: MutableMap<UUID, GameHistory> = mutableMapOf()
) : GameHistoryRepository {

    override fun findByUserId(userId: UUID): List<GameHistory> =
        dummy.values
            .filter { it.userId == userId }
            .toList()

    override fun save(gameHistory: GameHistory): GameHistory =
        gameHistory
            .also { dummy[gameHistory.id] = gameHistory }
            .let { gameHistory }


}