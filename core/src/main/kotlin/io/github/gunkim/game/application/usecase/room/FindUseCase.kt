package io.github.gunkim.game.application.usecase.room

import io.github.gunkim.game.domain.Room
import java.util.UUID

interface FindUseCase {
    fun find(): List<Room>
    fun find(userId: UUID, roomId: UUID): Room
    fun find(roomId: UUID): Room
}
