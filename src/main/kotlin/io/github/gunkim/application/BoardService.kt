package io.github.gunkim.application

import io.github.gunkim.domain.game.GamerRepository
import io.github.gunkim.domain.game.MoveType
import io.github.gunkim.domain.room.RoomRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BoardService(
    private val roomRepository: RoomRepository,
    private val gamerRepository: GamerRepository,
) {
    fun move(roomId: UUID, userId: UUID, type: MoveType) {
        val room = roomRepository.find(roomId)

        check(room.isStart) { "게임이 시작되지 않았습니다." }

        val gamer = room.findGamer(userId).move(type)
        gamerRepository.save(gamer)
    }
}
