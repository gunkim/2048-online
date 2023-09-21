package io.github.gunkim.domain.game

import java.util.*

data class Board(
    val id: UUID = UUID.randomUUID(),
    val rows: Rows
) {
    val score: Int
        get() = rows.score

    fun moveLeft(): Board = move(rows.moveLeft())

    fun moveRight(): Board = move(rows.moveRight())

    fun moveUp(): Board = move(rows.moveUp())

    fun moveDown(): Board = move(rows.moveDown())

    fun move(type: MoveType) = type.move(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    private fun move(rows: Rows): Board {
        val isMoved = this.rows != rows
        return if (isMoved) {
            copy(rows = rows.addLevel1CellWithRandomPosition())
        } else {
            copy(rows = rows)
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
