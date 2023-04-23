package io.github.gunkim.application

import io.github.gunkim.domain.Room
import java.util.UUID

interface CreateRoom {
    fun create(title: String, userId: UUID): Room
}
