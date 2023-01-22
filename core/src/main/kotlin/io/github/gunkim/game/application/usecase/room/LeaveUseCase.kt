package io.github.gunkim.game.application.usecase.room

import java.util.UUID

interface LeaveUseCase {
    fun leave(roomId: UUID, userId: UUID)
}
