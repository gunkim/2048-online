package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.Cell
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

    fun moveLeft(): Board {
        val content = rows.moveLeft()

        if(content.second) {
            return createBoard(content.first).generateRandomCell()
        }
        return createBoard(content.first)
    }
    fun moveRight(): Board {
        val content = rows.moveRight()

        if(content.second) {
            return createBoard(content.first).generateRandomCell()
        }
        return createBoard(content.first)
    }
    fun moveUp(): Board {
        val content = rows.moveUp()

        if(content.second) {
            return createBoard(content.first).generateRandomCell()
        }
        return createBoard(content.first)
    }
    fun moveDown(): Board {
        val content = rows.moveDown()

        if(content.second) {
            return createBoard(content.first).generateRandomCell()
        }
        return createBoard(content.first)
    }
    fun move(type: MoveType) = type.move(this)

    fun init(a: Pair<Int, Int>, b: Pair<Int, Int>): Board {
        return Board(id, rows.init(a.first, a.second).init(b.first, b.second))
    }

    fun generateRandomCell(): Board {
        var cnt = 0

        var rows: Rows = rows
        while(cnt < 2 && rows.isFull.not()) {
            val posX = (0..3).random()
            val posY = (0..3).random()

            if(rows[posX][posY] == Cell.ZERO) {
                rows = rows.init(posY, posX)
                cnt++
            }
        }
        return Board(id, rows)
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
