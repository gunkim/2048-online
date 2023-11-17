package io.github.gunkim.domain.game.event

import java.util.UUID

data class GameStopEvent(
    val roomId: UUID
)
