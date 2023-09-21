package io.github.gunkim.domain.game

import io.github.gunkim.domain.user.User
import java.util.UUID

data class Gamer(
    val user: User,
    val board: Board,
    val isHost: Boolean = false,
    val isReady: Boolean = false,
    var order: Int = -1,
) {
    val score: Int
        get() = board.score

    val id: UUID
        get() = user.id

    fun start() = copy(board = Board.start())
    fun move(moveType: MoveType) = copy(board = moveType(board))

    fun hasPlayerId(userId: UUID) = user.isSameId(userId)

    fun isSameUserId(userId: UUID) = user.isSameId(userId)

    fun host() = copy(isHost = true)

    fun unready() = copy(isReady = false)

    fun reverseReady() = copy(isReady = !isReady)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Gamer

        return user == other.user
    }

    override fun hashCode(): Int {
        return user.hashCode()
    }
}
