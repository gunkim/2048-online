package io.github.gunkim.game.application.service

import io.github.gunkim.game.application.usecase.board.MoveUseCase
import io.github.gunkim.game.domain.Gamers
import io.github.gunkim.game.domain.vo.MoveType
import org.springframework.stereotype.Service
import java.util.*

@Service
class BoardService(
    private val gamers: Gamers
) : MoveUseCase {
    override fun move(gamerId: UUID, type: MoveType) {
        val gamer = gamers.find(gamerId)

        gamers.save(gamer.move(type))
    }
}
