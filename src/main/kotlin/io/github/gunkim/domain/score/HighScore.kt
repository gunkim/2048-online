package io.github.gunkim.domain.score

import java.util.UUID

data class HighScore(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val score: Long = 0,
) {
    init {
        validScore(score)
    }

    fun isHigh(score: Long) = this.score < score

    fun changeRecord(score: Long) = score
        .also { validScore(score) }
        .let { copy(score = it) }

    private fun validScore(score: Long) = require(score >= 0) { "점수는 0 미만이 될 수 없습니다." }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HighScore

        return userId == other.userId
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }
}
