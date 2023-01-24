package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType
import java.util.UUID

data class Gamer(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val board: Board,
    val isHost: Boolean = false,
) {
    fun start() = Gamer(id, user, Board.start(), isHost)
    fun move(moveType: MoveType) = Gamer(id, user, moveType(board), isHost)

    fun hasPlayer(user: User) = this.user == user

    fun isSameUserId(userId: UUID) = user.isSameId(userId)
}
