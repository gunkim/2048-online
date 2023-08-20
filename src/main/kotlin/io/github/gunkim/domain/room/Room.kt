package io.github.gunkim.domain.room

import io.github.gunkim.domain.exception.LeaveHostException
import io.github.gunkim.domain.game.Board
import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.game.MoveType
import io.github.gunkim.domain.user.User
import java.time.LocalDateTime
import java.util.*

data class Room(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val gamers: List<Gamer>,
    val isStart: Boolean,
    val endedAt: LocalDateTime? = null,
) {
    init {
        require(title.isNotBlank()) { "방 제목은 공백일 수 없습니다." }
        require(title.length <= 20) { "방 제목은 20자 이하여야 합니다." }
        require(gamers.size <= 4) { "게임에 참여할 수 있는 인원은 최대 4명입니다." }
        require(gamers.isNotEmpty()) { "게임에 참여할 수 있는 인원은 최소 1명 이상입니다." }
        require(gamers.distinctBy { it.user.id }.size == gamers.size) { "중복된 유저가 있습니다." }
    }

    init {
        require(gamers.isNotEmpty()) { "게임에 참여할 수 있는 인원은 최소 1명 이상입니다." }
    }

    fun move(user: User, moveType: MoveType): Room {
        check(isStart) { "게임이 시작되지 않았습니다." }
        return Room(id, title, gamers.move(user, moveType), isStart)
    }

    fun start(user: User): Room {
        check(!isStart) { "이미 게임이 시작되었습니다." }
        require(gamers.find(user).isHost) { "시작은 방장만 할 수 있습니다." }
        check(gamers.size >= 2) { "게임에 참여할 수 있는 인원은 최소 2명 이상입니다." }
        check(gamers.all(Gamer::isReady)) { "게임에 참여한 모든 플레이어가 준비되어야 합니다." }

        val gamers = gamers.map(Gamer::start)
        gamers.forEachIndexed { index, gamer -> gamer.order = index }

        return copy(gamers = gamers, isStart = true, endedAt = LocalDateTime.now().plusSeconds(PLAY_TIME))
    }

    fun stop(user: User): Room {
        check(isStart) { "게임이 시작되지 않았습니다." }
        require(gamers.find(user).isHost) { "종료는 방장만 할 수 있습니다." }

        return copy(isStart = false)
    }

    fun stop() = copy(
        gamers = gamers.map(Gamer::unready),
        isStart = false,
    )

    fun hasUserId(userId: UUID) = gamers.any { it.user.id == userId }

    fun join(user: User): Room {
        check(!isStart) { "이미 시작된 게임에는 참여할 수 없습니다." }
        require(!gamers.hasId(user.id)) { "이미 참여한 유저입니다." }

        return copy(gamers = gamers + Gamer(user = user, board = Board.create()))
    }

    fun leave(user: User): Room {
        check(!isStart) { "이미 시작된 게임에는 나갈 수 없습니다." }
        require(gamers.hasId(user.id)) { "게임에 참여하지 않은 플레이어 입니다." }

        if (gamers.size == 1) {
            throw LeaveHostException()
        }

        val filteredGamers = gamers
            .filter { !it.hasPlayer(user) }
            .mapIndexed { index, gamer ->
                if (index == 0) {
                    gamer.host()
                } else {
                    gamer
                }
            }

        return copy(gamers = filteredGamers)
    }

    fun findGamer(user: User): Gamer = gamers.find(user)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun ready(user: User): Room {
        check(!isStart) { "이미 시작된 게임에는 준비할 수 없습니다." }

        val gamers = gamers.map {
            if (it.hasPlayer(user)) {
                it.reverseReady()
            } else {
                it
            }
        }
        return copy(gamers = gamers)
    }

    fun kick(user: User, gamerId: UUID): Room {
        require(gamers.find(user).isHost) { "강퇴는 방장만 할 수 있습니다." }
        check(!isStart) { "이미 시작된 게임에는 준비할 수 없습니다." }

        return copy(gamers = gamers.filter { it.id != gamerId })
    }

    companion object {
        const val PLAY_TIME = 30L

        fun start(title: String, gamers: List<Gamer>) =
            Room(title = title, gamers = gamers, isStart = true)

        fun stop(title: String, gamers: List<Gamer>) =
            Room(title = title, gamers = gamers, isStart = false)
    }
}

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
