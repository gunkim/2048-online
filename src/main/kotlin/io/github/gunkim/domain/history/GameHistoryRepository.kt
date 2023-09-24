package io.github.gunkim.domain.history

import java.util.UUID

interface GameHistoryRepository {

    fun findByUserId(userId: UUID): List<GameHistory>

    fun save(gameHistory: GameHistory): GameHistory
}
