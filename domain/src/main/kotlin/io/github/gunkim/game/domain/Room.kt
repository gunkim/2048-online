package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType

private fun List<Gamer>.move(player: Player, moveType: MoveType) = this.map {
    if (it.hasPlayer(player)) {
        it.move(moveType)
    } else {
        it
    }
}

private fun List<Gamer>.find(player: Player) = this.find { it.hasPlayer(player) }
    ?: throw IllegalArgumentException("게임에 참여하지 않은 플레이어 입니다.")

data class Room(
    val title: String,
    val gamers: List<Gamer>,
    val isStart: Boolean
) {
    init {
        require(gamers.isNotEmpty()) { "게임에 참여할 수 있는 인원은 최소 1명 이상입니다." }
    }

    fun move(player: Player, moveType: MoveType): Room {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }
        gamers.find(player).move(moveType)
        return start(title, gamers.move(player, moveType))
    }

    fun start(player: Player): Room {
        if (isStart) {
            throw IllegalStateException("이미 게임이 시작되었습니다.")
        }

        if (!gamers.find(player).isHost) {
            throw IllegalArgumentException("시작은 방장만 할 수 있습니다.")
        }

        return start(title, gamers)
    }

    fun stop(player: Player): Room {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }

        if (!gamers.find(player).isHost) {
            throw IllegalArgumentException("종료는 방장만 할 수 있습니다.")
        }

        return stop(title, gamers)
    }

    companion object {
        fun start(title: String, gamers: List<Gamer>) = Room(title, gamers, true)
        fun stop(title: String, gamers: List<Gamer>) = Room(title, gamers, false)
    }
}