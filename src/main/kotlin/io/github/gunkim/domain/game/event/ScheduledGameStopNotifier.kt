package io.github.gunkim.domain.game.event

import java.time.LocalDateTime

fun interface ScheduledGameStopNotifier {
    fun notify(event: GameStopEvent, endTime: LocalDateTime)
}
