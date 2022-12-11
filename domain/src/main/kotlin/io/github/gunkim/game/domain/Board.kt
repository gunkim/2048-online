package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType
import io.github.gunkim.game.domain.vo.Rows
import java.util.*

data class Board(
    val id: UUID = UUID.randomUUID(),
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
    fun move(type: MoveType) = type.move(this)

    private fun createBoard(rows: Rows) = Board(rows = rows)

    companion object {
        fun create() = Board(rows = Rows.empty())
    }
}
