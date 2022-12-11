package io.github.gunkim.game.domain

interface Boards {
    fun findByPlayer(id: Player): Board
    fun save(board: Board): Board
}
