package io.github.gunkim.game.application.usecase.room

import java.util.UUID

interface StartUseCase {
    fun start(roomId: UUID, userId: UUID)
}
