package io.github.gunkim.game.application.service

import io.github.gunkim.game.application.usecase.board.MoveUseCase
import io.github.gunkim.game.domain.Gamers
import io.github.gunkim.game.domain.Rooms
import io.github.gunkim.game.domain.Users
import io.github.gunkim.game.domain.vo.MoveType
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BoardService(
    private val rooms: Rooms,
    private val users: Users,
    private val gamers: Gamers,
) : MoveUseCase {
    override fun move(roomId: UUID, userId: UUID, type: MoveType) {
        val room = rooms.find(roomId)
        val user = users.find(userId)

        val gamer = room.move(user, type).findGamer(user)
        gamers.save(gamer)
    }
}
