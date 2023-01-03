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

    fun init(a: Pair<Int, Int>, b: Pair<Int, Int>): Board {
        return Board(id, rows.init(a.first, a.second).init(b.first, b.second))
    }

    private fun createBoard(rows: Rows) = Board(id, rows)

    companion object {
        fun create() = Board(rows = Rows.empty())

        fun start(): Board {
            val a =
                listOf(0, 1, 2, 3, 0, 1, 2, 3).shuffled().take(2).sorted().let { it[0] to it[1] }
            val b =
                listOf(0, 1, 2, 3, 0, 1, 2, 3).shuffled().take(2).sorted().let { it[0] to it[1] }

            return Board(rows = Rows.empty()).init(a, b)
        }
    }
}
