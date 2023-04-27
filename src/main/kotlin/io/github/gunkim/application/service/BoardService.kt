package io.github.gunkim.application.service

import io.github.gunkim.application.MoveBoard
import io.github.gunkim.domain.game.Gamers
import io.github.gunkim.domain.game.MoveType
import io.github.gunkim.domain.room.Rooms
import io.github.gunkim.domain.user.Users
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BoardService(
    private val rooms: Rooms,
    private val users: Users,
    private val gamers: Gamers,
) : MoveBoard {
    override fun move(roomId: UUID, userId: UUID, type: MoveType) {
        val room = rooms.find(roomId)
        val user = users.find(userId)

        val gamer = room.move(user, type).findGamer(user)
        gamers.save(gamer)
    }
}
