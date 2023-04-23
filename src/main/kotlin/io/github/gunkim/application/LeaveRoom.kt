package io.github.gunkim.application

import java.util.UUID

interface LeaveRoom {
    fun leave(roomId: UUID, userId: UUID): Boolean
}
