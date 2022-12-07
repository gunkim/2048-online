package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.Rows

data class Board(
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
    private fun createBoard(rows: Rows) = Board(rows)

    companion object {
        fun create() = Board(Rows.empty())
    }
}