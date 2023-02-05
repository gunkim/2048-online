package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.Cell
import io.github.gunkim.game.domain.vo.MoveType
import io.github.gunkim.game.domain.vo.Rows
import java.util.UUID

private fun generateRandomPoint(): Pair<Int, Int> = (0..3).random() to (0..3).random()

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

    fun init(a: Pair<Int, Int>, b: Pair<Int, Int>): Board {
        return Board(
            id,
            rows.init(a.first, a.second).init(b.first, b.second),
        )
    }

    private fun move(rows: Rows): Board {
        val board = Board(id, rows)

        val isMoved = board.rows != this.rows
        return if (isMoved) {
            board.addRandomCellAsLevel1()
        } else {
            board
        }
    }

    private fun addRandomCellAsLevel1(): Board {
        var cnt = 0

        var rows: Rows = rows
        while (cnt < RANDOM_GENERATE_CELL_CNT && rows.isFull.not()) {
            val posX = (0..3).random()
            val posY = (0..3).random()

            if (rows[posX][posY] == Cell.ZERO) {
                rows = rows.init(posY, posX)
                cnt++
            }
        }

        return Board(id, rows)
    }

    companion object {
        private const val RANDOM_GENERATE_CELL_CNT = 1

        fun create() = Board(rows = Rows.empty())

        fun start() = Board(rows = Rows.empty()).init(generateRandomPoint(), generateRandomPoint())
    }
}
