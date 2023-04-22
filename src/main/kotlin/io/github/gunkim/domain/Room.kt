package io.github.gunkim.domain

import io.github.gunkim.domain.exception.JoinedPlayerException
import io.github.gunkim.domain.exception.LeaveHostException
import io.github.gunkim.domain.vo.MoveType
import java.util.UUID

private fun List<Gamer>.move(user: User, moveType: MoveType) = this.map {
    if (it.hasPlayer(user)) {
        it.move(moveType)
    } else {
        it
    }
}

private fun List<Gamer>.find(user: User) = this.find { it.hasPlayer(user) }
    ?: throw IllegalArgumentException("게임에 참여하지 않은 플레이어 입니다.")

private fun List<Gamer>.hasId(id: UUID) = this.any { it.isSameUserId(id) }

data class Room(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val gamers: List<Gamer>,
    val isStart: Boolean,
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
        return Room(id, title, gamers.move(user, moveType), isStart)
    }

    fun start(user: User): Room {
        if (isStart) {
            throw IllegalStateException("이미 게임이 시작되었습니다.")
        }

        if (!gamers.find(user).isHost) {
            throw IllegalArgumentException("시작은 방장만 할 수 있습니다.")
        }

        return Room(id, title, gamers.map(Gamer::start), true)
    }

    fun stop(user: User): Room {
        if (!isStart) {
            throw IllegalStateException("게임이 시작되지 않았습니다.")
        }

        if (!gamers.find(user).isHost) {
            throw IllegalArgumentException("종료는 방장만 할 수 있습니다.")
        }

        return Room(id, title, gamers, false)
    }

    fun hasGamerId(gamerId: UUID) = gamers.hasId(gamerId)
    fun hasUserId(userId: UUID) = gamers.any { it.user.id == userId }

    fun join(user: User): Room {
        if (isStart) {
            throw IllegalStateException("이미 시작된 게임에는 참여할 수 없습니다.")
        }

        if (gamers.hasId(user.id)) {
            throw JoinedPlayerException()
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

        if (gamers.size == 1) {
            throw LeaveHostException()
        }

        val gamers = gamers
            .filter { !it.hasPlayer(user) }
            .mapIndexed { index, gamer ->
                if (index == 0) {
                    gamer.host()
                } else {
                    gamer
                }
            }
        return Room(id, title, gamers, isStart)
    }

    fun findGamer(user: User): Gamer = gamers.find(user)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun ready(user: User): Room {
        if (isStart) {
            throw IllegalStateException("이미 시작된 게임에는 준비할 수 없습니다.")
        }

        val gamers = gamers.map {
            if (it.hasPlayer(user) || !it.isHost) it.ready()
            else it
        }
        return Room(id, title, gamers, isStart)
    }

    companion object {
        fun start(title: String, gamers: List<Gamer>) =
            Room(title = title, gamers = gamers, isStart = true)

        fun stop(title: String, gamers: List<Gamer>) =
            Room(title = title, gamers = gamers, isStart = false)
    }
}
