package io.github.gunkim.application

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.room.Room
import io.github.gunkim.domain.room.RoomRepository
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
    private val roomRepository: RoomRepository,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    fun stopGame() {
        val targetRooms = roomRepository.find()
            .filter(Room::isStart)
            .filter { it.endedAt!!.isBefore(LocalDateTime.now()) }

        targetRooms.forEach { room ->
            roomRepository.save(room.stop())

            messagingTemplate.convertAndSend(
                "/topic/rooms/${room.id}/game-end", room.gamers
                    .map(StopGameScheduler::GamerResponse)
                    .sortedByDescending(GamerResponse::score)
            )
        }
    }

    data class GamerResponse(
        val id: UUID,
        val profileImageUrl: String?,
        val name: String,
        val score: Int,
    ) {
        constructor(gamer: Gamer) : this(
            gamer.user.id,
            gamer.user.profileImageUrl,
            gamer.user.name,
            gamer.score
        )
    }
}
