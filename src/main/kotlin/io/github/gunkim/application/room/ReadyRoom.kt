package io.github.gunkim.application.room

import java.util.UUID

fun interface ReadyRoom {
    fun ready(userId: UUID, roomId: UUID)
}
