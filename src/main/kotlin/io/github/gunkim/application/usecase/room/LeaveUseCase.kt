package io.github.gunkim.application.usecase.room

import java.util.UUID

interface LeaveUseCase {
    fun leave(roomId: UUID, userId: UUID): Boolean
}
