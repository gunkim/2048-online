package io.github.gunkim.application.usecase.room

import io.github.gunkim.domain.Room
import java.util.UUID

interface CreateUseCase {
    fun create(title: String, userId: UUID): Room
}
