package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType

private fun hasPlayer(player: Player): (Board) -> Boolean {
    return fun(board: Board) = board.hasPlayer(player)
}

data class Room(
    val title: String,
    val boards: List<Board>,
    val isStart: Boolean = false
) {
    init {
        require(boards.size > 1) { "게임에 참여할 수 있는 인원은 최소 2명 이상입니다." }
    }

    fun move(player: Player, moveType: MoveType): Room {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }
        val confirmHasPlayer = hasPlayer(player)

        val boards: List<Board> = boards.map {
            if (confirmHasPlayer(it)) {
                moveType(it)
            } else {
                it
            }
        }
        return Room(title, boards, true)
    }

    fun start(player: Player): Room {
        if (isStart) {
            throw IllegalStateException("이미 게임이 시작되었습니다.")
        }

        val idx: Int = boards.indexOfFirst { it.hasPlayer(player) }
        if (idx != HOST_IDX) {
            throw IllegalArgumentException("시작은 방장만 할 수 있습니다.")
        }

        return Room(title, boards, true)
    }

    fun stop(player: Player) {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }
        val idx: Int = boards.indexOfFirst { it.hasPlayer(player) }
        if (idx != HOST_IDX) {
            throw IllegalArgumentException("종료는 방장만 할 수 있습니다.")
        }
        Room(title, boards, false)
    }

    companion object {
        private const val HOST_IDX = 0
    }
}