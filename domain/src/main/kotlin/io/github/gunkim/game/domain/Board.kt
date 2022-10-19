package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.Rows

data class Board(
    private val rows: Rows
) {
    fun moveLeft() = Board(rows.moveLeft())
    fun moveRight() = Board(rows.moveRight())
    fun moveUp() = Board(rows.moveUp())
    fun moveDown() = Board(rows.moveDown())
}