package io.github.gunkim.game.application.service

import io.github.gunkim.game.application.usecase.room.*
import io.github.gunkim.game.domain.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomService(
    private val users: Users,
    private val rooms: Rooms
) : FindUseCase, JoinUseCase, LeaveUseCase, StartUseCase, CreateUseCase {
    override fun find() = rooms.find()

    override fun find(gamerId: UUID, roomId: UUID) = rooms.find(roomId)
        .also { validate(it, gamerId, roomId) }

    override fun join(roomId: UUID, userId: UUID) {
        val (user, room) = load(userId, roomId)

        rooms.save(room.join(user))
    }

    override fun start(roomId: UUID, userId: UUID) {
        val (user, room) = load(userId, roomId)

        rooms.save(room.start(user))
    }

    override fun leave(roomId: UUID, userId: UUID) {
        val (user, room) = load(userId, roomId)

        rooms.save(room.leave(user))
    }

    override fun create(title: String, userId: UUID): Room {
        val user = users.find(userId)

        if(rooms.existByUserId(user.id)) {
            throw IllegalArgumentException("이미 방에 참여중입니다.")
        }

        val hostGamer = Gamer(user = user, board = Board.create(), isHost = true)
        val room = Room(title = title, gamers = listOf(hostGamer), isStart = false)

        return rooms.save(room)
    }

    private fun load(userId: UUID, roomId: UUID) = users.find(userId) to rooms.find(roomId)

    private fun validate(
        room: Room,
        gamerId: UUID,
        roomId: UUID
    ) {
        if (!room.hasGamerId(gamerId)) {
            throw IllegalArgumentException("해당 플레이어는 방에 참여하지 않았습니다. (gamer_id : $gamerId, room_id : $roomId)")
        }
    }
}
