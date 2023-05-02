package io.github.gunkim.application

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.room.Room
import io.github.gunkim.domain.room.Rooms
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Component
@EnableScheduling
class StopGameScheduler(
    private val rooms: Rooms,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    fun stopGame() {
        val targetRooms = rooms.find()
            .filter(Room::isStart)
            .filter { it.endedAt!!.isBefore(LocalDateTime.now()) }

        targetRooms.forEach {
            rooms.save(it.stop())

            messagingTemplate.convertAndSend("/topic/rooms/${it.id}/game-end", GameResultResponse(it))
        }
    }

    data class GameResultResponse(
        val gamers: List<GamerResponse>
    ) {
        constructor(room: Room) : this(room.gamers.map(::GamerResponse))
    }

    data class GamerResponse(
        val id: UUID,
        val name: String,
        val score: Int,
        val isWinner: Boolean,
    ) {
        constructor(gamer: Gamer) : this(gamer.user.id, gamer.user.name, gamer.score, false)
    }
}
