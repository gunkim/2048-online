package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType
import io.github.gunkim.game.domain.vo.Rows
import java.util.UUID

data class Board(
    val id: UUID = UUID.randomUUID(),
    val rows: Rows,
) {
    val score: Int
        get() = rows.score

    val isGameWin: Boolean
        get() = rows.isGameWin

    fun moveLeft(): Board = move(rows.moveLeft())

    fun moveRight(): Board = move(rows.moveRight())

    fun moveUp(): Board = move(rows.moveUp())

    fun moveDown(): Board = move(rows.moveDown())

    fun move(type: MoveType) = type.move(this)

    private fun move(rows: Rows): Board {
        val isMoved = this.rows != rows
        return if (isMoved) {
            Board(id, rows.addLevel1CellWithRandomPosition())
        } else {
            Board(id, rows)
        }
    }

    companion object {
        fun create() = Board(rows = Rows.empty())

        fun start() = Board(
            rows = Rows
                .empty()
                .addLevel1CellWithRandomPosition()
                .addLevel1CellWithRandomPosition()
        )
    }
}
