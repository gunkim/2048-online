package io.github.gunkim.application

import io.github.gunkim.domain.score.ScoreRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ScoreService(
    private val scoreRepository: ScoreRepository,
) {
    fun checkHighScore(newScore: Long, userId: UUID): Boolean = scoreRepository
        .findByUserId(userId)
        .isHigh(newScore)

    fun changeHighScore(newScore: Long, userId: UUID) = scoreRepository
        .findByUserId(userId)
        .changeRecord(newScore)
        .let { scoreRepository.save(it) }

    fun getHighScore(userId: UUID) = scoreRepository.findByUserId(userId)
}