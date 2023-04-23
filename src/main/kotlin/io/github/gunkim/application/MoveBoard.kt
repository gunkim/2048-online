package io.github.gunkim.application

import io.github.gunkim.domain.vo.MoveType
import java.util.UUID

interface MoveBoard {
    fun move(roomId: UUID, userId: UUID, type: MoveType)
}
