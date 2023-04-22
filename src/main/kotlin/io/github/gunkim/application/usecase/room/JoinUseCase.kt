package io.github.gunkim.application.usecase.room

import java.util.UUID

interface JoinUseCase {
    fun join(roomId: UUID, userId: UUID)
}
