package io.github.gunkim.domain.game.event

fun interface ScheduledGameStopNotifier {
    fun notify(event: GameStopEvent)
}
