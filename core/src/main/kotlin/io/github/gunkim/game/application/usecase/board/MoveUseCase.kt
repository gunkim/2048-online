package io.github.gunkim.game.application.usecase.board

import io.github.gunkim.game.domain.vo.MoveType
import java.util.*

interface MoveUseCase {
    fun move(gamerId: UUID, type: MoveType)
}
