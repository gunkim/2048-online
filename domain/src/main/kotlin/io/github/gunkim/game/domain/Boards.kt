package io.github.gunkim.game.domain

interface Boards {
    fun findByPlayer(id: User): Board
    fun save(board: Board): Board
}
