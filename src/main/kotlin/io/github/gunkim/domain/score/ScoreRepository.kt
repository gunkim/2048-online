package io.github.gunkim.domain.score

import java.util.UUID

interface ScoreRepository {
    fun save(score: ScoreHistory): ScoreHistory

    fun findHighScore(userId: UUID): ScoreHistory
}
