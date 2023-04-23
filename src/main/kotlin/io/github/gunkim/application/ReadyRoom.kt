package io.github.gunkim.application

import java.util.UUID

fun interface ReadyRoom {
    fun ready(userId: UUID, roomId: UUID)
}
