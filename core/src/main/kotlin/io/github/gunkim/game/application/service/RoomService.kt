package io.github.gunkim.game.application.service

import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.application.usecase.room.JoinUseCase
import io.github.gunkim.game.application.usecase.room.LeaveUseCase
import io.github.gunkim.game.application.usecase.room.StartUseCase
import io.github.gunkim.game.domain.Room
import io.github.gunkim.game.domain.Rooms
import io.github.gunkim.game.domain.Users
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomService(
    private val users: Users,
    private val rooms: Rooms
) : FindUseCase, JoinUseCase, LeaveUseCase, StartUseCase {
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
