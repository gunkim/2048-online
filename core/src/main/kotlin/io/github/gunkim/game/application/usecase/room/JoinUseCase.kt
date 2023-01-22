package io.github.gunkim.game.application.usecase.room

import java.util.UUID

interface JoinUseCase {
    fun join(roomId: UUID, userId: UUID)
}
