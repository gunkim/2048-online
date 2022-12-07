package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType

class Gamer(
    val player: Player,
    val board: Board,
    val isHost: Boolean = false
) {
    fun move(moveType: MoveType) = Gamer(player, moveType(board))

    fun hasPlayer(player: Player) = this.player == player
}