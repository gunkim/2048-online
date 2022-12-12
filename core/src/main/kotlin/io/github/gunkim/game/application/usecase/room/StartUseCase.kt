package io.github.gunkim.game.application.usecase.room

import java.util.*

interface StartUseCase {
    fun start(roomId: UUID, userId: UUID)
}
