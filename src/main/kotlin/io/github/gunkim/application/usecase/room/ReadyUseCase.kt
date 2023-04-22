package io.github.gunkim.application.usecase.room

import java.util.UUID

fun interface ReadyUseCase {
    fun ready(userId: UUID, roomId: UUID)
}
