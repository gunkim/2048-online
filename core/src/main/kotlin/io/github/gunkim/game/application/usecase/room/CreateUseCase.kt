package io.github.gunkim.game.application.usecase.room

import io.github.gunkim.game.domain.Room
import java.util.UUID

interface CreateUseCase {
    fun create(title: String, userId: UUID): Room
}
