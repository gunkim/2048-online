package io.github.gunkim.application.room

import io.github.gunkim.domain.room.Room
import java.util.UUID

interface FindRoom {
    fun find(): List<Room>
    fun find(userId: UUID, roomId: UUID): Room
    fun find(roomId: UUID): Room
}
