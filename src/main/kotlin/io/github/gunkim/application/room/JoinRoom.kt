package io.github.gunkim.application.room

import java.util.UUID

interface JoinRoom {
    fun join(roomId: UUID, userId: UUID)
}
