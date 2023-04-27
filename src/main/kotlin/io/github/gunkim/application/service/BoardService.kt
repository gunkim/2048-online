package io.github.gunkim.application.service

import io.github.gunkim.application.MoveBoard
import io.github.gunkim.domain.Gamers
import io.github.gunkim.domain.Rooms
import io.github.gunkim.domain.Users
import io.github.gunkim.domain.vo.MoveType
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
