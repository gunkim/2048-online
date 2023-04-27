package io.github.gunkim.application.room

import java.util.UUID

interface StartRoom {
    fun start(roomId: UUID, userId: UUID)
}
