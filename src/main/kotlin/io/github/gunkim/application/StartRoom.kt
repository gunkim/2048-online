package io.github.gunkim.application

import java.util.UUID

interface StartRoom {
    fun start(roomId: UUID, userId: UUID)
}
