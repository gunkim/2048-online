package io.github.gunkim.game.application.usecase.room

import java.util.UUID

fun interface ReadyUseCase {
    fun ready(userId: UUID, roomId: UUID)
}
