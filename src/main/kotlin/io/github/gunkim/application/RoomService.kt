package io.github.gunkim.application

import io.github.gunkim.domain.exception.LeaveHostException
import io.github.gunkim.domain.game.Board
import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.room.Room
import io.github.gunkim.domain.room.RoomRepository
import io.github.gunkim.domain.user.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RoomService(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
) {
    fun find() = roomRepository.find()

    fun find(userId: UUID, roomId: UUID) = roomRepository.find(roomId)
        .also { validate(it, userId, roomId) }

    fun find(roomId: UUID) = roomRepository.find(roomId)

    fun join(roomId: UUID, userId: UUID) {
        val (user, room) = load(userId, roomId)

        require(!roomRepository.existByUserId(user.id)) { "이미 방에 참여중입니다." }

        roomRepository.save(room.join(user))
    }

    fun start(roomId: UUID, userId: UUID) {
        val room = roomRepository.find(roomId)

        roomRepository.save(room.start(userId))
    }

    fun leave(roomId: UUID, userId: UUID): Boolean {
        val room = roomRepository.find(roomId)

        return try {
            roomRepository.save(room.leave(userId))
            true
        } catch (e: LeaveHostException) {
            roomRepository.delete(room)
            false
        }
    }

    fun create(title: String, userId: UUID): Room {
        val user = userRepository.find(userId)

        require(!roomRepository.existByUserId(user.id)) { "이미 방에 참여중입니다." }

        val hostGamer = Gamer(user = user, board = Board.create(), isHost = true)
        val room = Room(title = title, gamers = listOf(hostGamer), isStart = false)

        return roomRepository.save(room)
    }

    fun ready(userId: UUID, roomId: UUID) {
        val room = roomRepository.find(roomId)

        roomRepository.save(room.ready(userId))
    }

    private fun load(userId: UUID, roomId: UUID) = userRepository.find(userId) to roomRepository.find(roomId)

    private fun validate(
        room: Room,
        userId: UUID,
        roomId: UUID,
    ) {
        require(room.hasUserId(userId)) { "해당 플레이어는 방에 참여하지 않았습니다. (gamer_id : $userId, room_id : $roomId)" }
    }

    fun kick(roomId: UUID, managerId: UUID, gamerId: UUID) {
        val room = roomRepository.find(roomId)

        roomRepository.save(room.kick(managerId, gamerId))
    }
}
