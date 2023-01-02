package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType
import java.util.*

private fun List<Gamer>.move(user: User, moveType: MoveType) = this.map {
    if (it.hasPlayer(user)) {
        it.move(moveType)
    } else {
        it
    }
}

private fun List<Gamer>.find(user: User) = this.find { it.hasPlayer(user) }
    ?: throw IllegalArgumentException("게임에 참여하지 않은 플레이어 입니다.")

private fun List<Gamer>.hasId(id: UUID) = this.any { it.id == id }

data class Room(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val gamers: List<Gamer>,
    val isStart: Boolean
) {
    val hostName: String
        get() = gamers.find { it.isHost }?.user?.name ?: throw IllegalStateException("방장이 없습니다.")

    init {
        require(gamers.isNotEmpty()) { "게임에 참여할 수 있는 인원은 최소 1명 이상입니다." }
    }

    fun move(user: User, moveType: MoveType): Room {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }
        gamers.find(user).move(moveType)
        return start(title, gamers.move(user, moveType))
    }

    fun start(user: User): Room {
        if (isStart) {
            throw IllegalStateException("이미 게임이 시작되었습니다.")
        }

        if (!gamers.find(user).isHost) {
            throw IllegalArgumentException("시작은 방장만 할 수 있습니다.")
        }

        return start(title, gamers)
    }

    fun stop(user: User): Room {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }

        if (!gamers.find(user).isHost) {
            throw IllegalArgumentException("종료는 방장만 할 수 있습니다.")
        }

        return stop(title, gamers)
    }

    fun hasGamerId(gamerId: UUID) = gamers.hasId(gamerId)
    fun hasUserId(userId: UUID) = gamers.any { it.user.id == userId }

    fun join(user: User): Room {
        if (isStart) {
            throw IllegalStateException("이미 시작된 게임에는 참여할 수 없습니다.")
        }

        if (gamers.hasId(user.id)) {
            throw IllegalArgumentException("이미 게임에 참여한 플레이어 입니다.")
        }

        val gamers = gamers + Gamer(user = user, board = Board.create())
        return Room(id, title, gamers, isStart)
    }

    fun leave(user: User): Room {
        if (isStart) {
            throw IllegalStateException("이미 시작된 게임에는 나갈 수 없습니다.")
        }

        if (!gamers.hasId(user.id)) {
            throw IllegalArgumentException("게임에 참여하지 않은 플레이어 입니다.")
        }

        val gamers = gamers.filter { !it.hasPlayer(user) }
        return Room(id, title, gamers, isStart)
    }

    companion object {
        fun start(title: String, gamers: List<Gamer>) =
            Room(title = title, gamers = gamers, isStart = true)

        fun stop(title: String, gamers: List<Gamer>) =
            Room(title = title, gamers = gamers, isStart = false)
    }
}
