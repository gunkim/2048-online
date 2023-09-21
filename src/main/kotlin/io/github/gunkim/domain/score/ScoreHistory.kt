package io.github.gunkim.domain.score

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.UUID

data class ScoreHistory(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val score: Int = 0,
    val createdAt: LocalDateTime = now(),
) {
    init {
        require(score >= 0) { "점수는 0 미만이 될 수 없습니다." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScoreHistory

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
