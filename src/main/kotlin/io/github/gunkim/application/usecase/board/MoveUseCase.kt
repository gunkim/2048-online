package io.github.gunkim.application.usecase.board

import io.github.gunkim.domain.vo.MoveType
import java.util.UUID

interface MoveUseCase {
    fun move(roomId: UUID, userId: UUID, type: MoveType)
}
