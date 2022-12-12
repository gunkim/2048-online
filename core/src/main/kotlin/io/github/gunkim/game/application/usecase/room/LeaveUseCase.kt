package io.github.gunkim.game.application.usecase.room

import java.util.*

interface LeaveUseCase {
    fun leave(roomId: UUID, userId: UUID)
}
