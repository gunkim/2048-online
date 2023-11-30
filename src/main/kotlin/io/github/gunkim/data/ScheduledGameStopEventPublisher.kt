package io.github.gunkim.data

import io.github.gunkim.domain.game.event.GameStopEvent
import io.github.gunkim.domain.game.event.ScheduledGameStopNotifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ScheduledGameStopEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) : ScheduledGameStopNotifier {
    override fun notify(event: GameStopEvent, endTime: LocalDateTime) {
        scheduler.schedule(
            { eventPublisher.publishEvent(event) },
            delayTime(endTime),
            TimeUnit.SECONDS
        )
    }

    private fun delayTime(endTime: LocalDateTime) = Duration.between(LocalDateTime.now(), endTime).toSeconds()

    companion object {
        private val scheduler = Executors.newScheduledThreadPool(4)
    }
}
