package io.github.gunkim.domain.game

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
            copy(rows = rows.addLevel1CellWithRandomPosition())
        } else {
            copy(rows = rows)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun create() = Board(rows = Rows.empty())

        fun start() = Board(
            rows = Rows
                .empty()
                .addLevel1CellWithRandomPosition()
                .addLevel1CellWithRandomPosition(),
        )
    }
}
