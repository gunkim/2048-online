package io.github.gunkim.domain.history

import java.util.UUID

data class GameHistory(
    val id: UUID = UUID.randomUUID(),
    val roomId: UUID,
    val userId: UUID,
    val score: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameHistory

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
