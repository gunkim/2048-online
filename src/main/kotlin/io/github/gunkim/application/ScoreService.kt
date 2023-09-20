package io.github.gunkim.application

import io.github.gunkim.domain.score.ScoreHistory
import io.github.gunkim.domain.score.ScoreRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ScoreService(
    private val scoreRepository: ScoreRepository,
) {
    fun saveScore(score: Int, userId: UUID) =
        scoreRepository.save(ScoreHistory(score = score, userId = userId))

    fun getHighScore(userId: UUID) =
        scoreRepository.findHighScore(userId)
}
