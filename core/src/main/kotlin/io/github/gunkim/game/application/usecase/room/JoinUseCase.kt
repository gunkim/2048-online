package io.github.gunkim.game.application.usecase.room

import java.util.*

interface JoinUseCase {
    fun join(roomId: UUID, userId: UUID)
}
