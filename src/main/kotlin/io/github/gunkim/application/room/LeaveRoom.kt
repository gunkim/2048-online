package io.github.gunkim.application.room

import java.util.UUID

interface LeaveRoom {
    fun leave(roomId: UUID, userId: UUID): Boolean
}
