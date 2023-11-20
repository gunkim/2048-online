package io.github.gunkim.application

import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.game.event.GameStopEvent
import io.github.gunkim.domain.room.RoomRepository
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GameStopEventListener(
    private val roomRepository: RoomRepository,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @EventListener
    fun handleOn(event: GameStopEvent) {
        val endedGameRoom = event.roomId.let {
            val findedRoom = roomRepository.find(it)
            roomRepository.save(findedRoom.stop())
        }

        messagingTemplate.convertAndSend(
            "/topic/rooms/${endedGameRoom.id}/game-end",
            endedGameRoom.gamers
                .map { GamerResponse(it) }
                .sortedByDescending(GamerResponse::score)
        )
    }

    data class GamerResponse(
        val id: UUID,
        val profileImageUrl: String?,
        val name: String,
        val score: Int
    ) {
        constructor(gamer: Gamer) : this(
            gamer.user.id,
            gamer.user.profileImageUrl,
            gamer.user.name,
            gamer.score
        )
    }
}
