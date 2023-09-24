package io.github.gunkim.application

import io.github.gunkim.domain.history.GameHistory
import io.github.gunkim.domain.history.GameHistoryRepository
import io.github.gunkim.domain.room.Room
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GameHistoryService(
    private val gameHistoryRepository: GameHistoryRepository
) {

    fun findByUserId(userId: UUID) = gameHistoryRepository.findByUserId(userId)

    fun save(room: Room) = room
        .let {
            it.gamers.map { gamer ->
                GameHistory(
                    roomId = room.id,
                    userId = gamer.id,
                    score = gamer.score
                )
            }
        }
        .let { gameHistories ->
            gameHistories.forEach { gameHistoryRepository.save(it) }
            gameHistories
        }
}
