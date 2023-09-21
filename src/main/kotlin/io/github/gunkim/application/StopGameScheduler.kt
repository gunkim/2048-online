package io.github.gunkim.application

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.room.Room
import io.github.gunkim.domain.room.RoomRepository
import io.github.gunkim.domain.score.ScoreHistory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
@EnableScheduling
class StopGameScheduler(
    private val roomRepository: RoomRepository,
    private val scoreService: ScoreService,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    fun stopGame() {
        val targetRooms = roomRepository.find()
            .filter(Room::isStart)
            .filter { it.endedAt!!.isBefore(LocalDateTime.now()) }

        targetRooms.forEach { room ->
            roomRepository.save(room.stop())

            val highScores = room.gamers
                .map { scoreService.saveScore(it.score, it.id) }
                .map { scoreService.getHighScore(it.userId) }

            messagingTemplate.convertAndSend(
                "/topic/rooms/${room.id}/game-end",
                room.gamers
                    .map { GamerResponse(it, highScores) }
                    .sortedByDescending(GamerResponse::score),
            )
        }
    }

    data class GamerResponse(
        val id: UUID,
        val profileImageUrl: String?,
        val name: String,
        val score: Int,
        val highScore: Int,
    ) {
        constructor(gamer: Gamer, highScores: List<ScoreHistory>) : this(
            gamer.user.id,
            gamer.user.profileImageUrl,
            gamer.user.name,
            gamer.score,
            highScores.first { it.userId == gamer.user.id }.score,
        )
    }
}
