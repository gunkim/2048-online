package io.github.gunkim.application.board

import io.github.gunkim.domain.game.MoveType
import java.util.UUID

interface MoveBoard {
    fun move(roomId: UUID, userId: UUID, type: MoveType)
}