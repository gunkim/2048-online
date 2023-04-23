package io.github.gunkim.application

import java.util.UUID

interface JoinRoom {
    fun join(roomId: UUID, userId: UUID)
}
