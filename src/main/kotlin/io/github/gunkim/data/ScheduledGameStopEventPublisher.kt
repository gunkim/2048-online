package io.github.gunkim.data

import io.github.gunkim.domain.game.event.GameStopEvent
import io.github.gunkim.domain.game.event.ScheduledGameStopNotifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ScheduledGameStopEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) : ScheduledGameStopNotifier {
    override fun notify(event: GameStopEvent) {
        scheduler.schedule(
            { eventPublisher.publishEvent(event) },
            DELAY_SECOND_TIME,
            TimeUnit.SECONDS
        )
    }

    companion object {
        // TODO 게임 시간을 30초로 설정, 게임 시간을 설정하는 변수가 여기에 있는 게 맞는지는 검토해볼 필요가 있음.
        // 게임 시간이 늘어나거나 줄어들거나 혹은 동적으로 변경될 수 있기 때문임.
        private const val DELAY_SECOND_TIME = 30L

        private val scheduler = Executors.newScheduledThreadPool(4)
    }
}
