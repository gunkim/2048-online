package io.github.gunkim.application.usecase.room

import io.github.gunkim.domain.Room
import java.util.UUID

interface FindUseCase {
    fun find(): List<Room>
    fun find(userId: UUID, roomId: UUID): Room
    fun find(roomId: UUID): Room
}
