package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.Rows

data class Board(
    val player: Player,
    val rows: Rows
) {
    val score: Int
        get() = rows.score

    val isGameWin: Boolean
        get() = rows.isGameWin

    fun moveLeft() = createBoard(rows.moveLeft())
    fun moveRight() = createBoard(rows.moveRight())
    fun moveUp() = createBoard(rows.moveUp())
    fun moveDown() = createBoard(rows.moveDown())
    fun hasPlayer(player: Player) = this.player == player

    private fun createBoard(rows: Rows) = Board(player, rows)

    companion object {
        fun create(player: Player) = Board(player, Rows.empty())
    }
}