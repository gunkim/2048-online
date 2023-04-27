package io.github.gunkim.domain.game

import io.github.gunkim.domain.user.User
import java.util.*

data class Gamer(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val board: Board,
    val isHost: Boolean = false,
    val isReady: Boolean = false,
) {
    val score: Int
        get() = board.score

    fun start() = copy(board = Board.start())
    fun move(moveType: MoveType) = copy(board = moveType(board))

    fun hasPlayer(user: User) = this.user == user

    fun isSameUserId(userId: UUID) = user.isSameId(userId)

    fun host() = copy(isHost = true)

    fun ready() = copy(isReady = !isReady)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Gamer

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
