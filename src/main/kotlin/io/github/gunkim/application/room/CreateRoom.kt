package io.github.gunkim.application.room

import io.github.gunkim.domain.room.Room
import java.util.UUID

interface CreateRoom {
    fun create(title: String, userId: UUID): Room
}
