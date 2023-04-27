package io.github.gunkim.application.room

import io.github.gunkim.domain.exception.LeaveHostException
import io.github.gunkim.domain.game.Board
import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.room.Room
import io.github.gunkim.domain.room.Rooms
import io.github.gunkim.domain.user.Users
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RoomService(
    private val users: Users,
    private val rooms: Rooms,
) : FindRoom, JoinRoom, LeaveRoom, StartRoom, CreateRoom, ReadyRoom {
    override fun find() = rooms.find()

    override fun find(userId: UUID, roomId: UUID) = rooms.find(roomId)
        .also { validate(it, userId, roomId) }

    override fun find(roomId: UUID) = rooms.find(roomId)

    override fun join(roomId: UUID, userId: UUID) {
        val (user, room) = load(userId, roomId)

        if (rooms.existByUserId(user.id)) {
            throw IllegalArgumentException("이미 방에 참여중입니다.")
        }

        rooms.save(room.join(user))
    }

    override fun start(roomId: UUID, userId: UUID) {
        val (user, room) = load(userId, roomId)

        rooms.save(room.start(user))
    }

    override fun leave(roomId: UUID, userId: UUID): Boolean {
        val (user, room) = load(userId, roomId)

        return try {
            rooms.save(room.leave(user))
            true
        } catch (e: LeaveHostException) {
            rooms.delete(room)
            false
        }
    }

    override fun create(title: String, userId: UUID): Room {
        val user = users.find(userId)

        if (rooms.existByUserId(user.id)) {
            throw IllegalArgumentException("이미 방에 참여중입니다.")
        }

        val hostGamer = Gamer(user = user, board = Board.create(), isHost = true)
        val room = Room(title = title, gamers = listOf(hostGamer), isStart = false)

        return rooms.save(room)
    }

    override fun ready(userId: UUID, roomId: UUID) {
        val (user, room) = load(userId, roomId)

        rooms.save(room.ready(user))
    }

    private fun load(userId: UUID, roomId: UUID) = users.find(userId) to rooms.find(roomId)

    private fun validate(
        room: Room,
        userId: UUID,
        roomId: UUID,
    ) {
        if (!room.hasUserId(userId)) {
            throw IllegalArgumentException("해당 플레이어는 방에 참여하지 않았습니다. (gamer_id : $userId, room_id : $roomId)")
        }
    }
}
