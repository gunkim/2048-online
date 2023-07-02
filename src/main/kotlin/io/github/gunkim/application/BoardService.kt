package io.github.gunkim.application

import io.github.gunkim.domain.game.GamerRepository
import io.github.gunkim.domain.game.MoveType
import io.github.gunkim.domain.room.RoomRepository
import io.github.gunkim.domain.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoardService(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val gamerRepository: GamerRepository,
) {
    fun move(roomId: UUID, userId: UUID, type: MoveType) {
        val room = roomRepository.find(roomId)
        val user = userRepository.find(userId)

        if (!room.isStart) {
            error("게임이 시작되지 않았습니다.")
        }

        val gamer = room.findGamer(user).move(type)
        gamerRepository.save(gamer)
    }
}
