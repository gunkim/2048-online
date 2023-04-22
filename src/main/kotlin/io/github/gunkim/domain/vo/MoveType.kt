package io.github.gunkim.domain.vo

import io.github.gunkim.domain.Board

enum class MoveType(
    val move: (Board) -> Board,
) {
    UNKNOWN({ _ -> throw IllegalArgumentException("Unknown move type") }),
    LEFT(Board::moveLeft),
    RIGHT(Board::moveRight),
    TOP(Board::moveUp),
    DOWN(Board::moveDown),
    ;

    operator fun invoke(board: Board) = move(board)
}
