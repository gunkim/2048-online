package io.github.gunkim.domain.score

import java.util.UUID

interface ScoreRepository {

    fun save(score: HighScore): HighScore

    fun findByUserId(userId: UUID): HighScore

    fun findAll(): List<HighScore>
}