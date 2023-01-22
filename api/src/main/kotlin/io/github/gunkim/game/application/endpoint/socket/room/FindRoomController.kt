package io.github.gunkim.game.application.endpoint.socket.room

import io.github.gunkim.game.application.common.SessionUserId
import io.github.gunkim.game.application.endpoint.socket.room.response.RoomResponse
import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.domain.Room
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

interface FindRoomController {
    @GetMapping("/rooms")
    fun find(): List<RoomResponse>

    @GetMapping("/rooms/{roomId}")
    fun findDetails(@SessionUserId sessionUserId: UUID, @PathVariable roomId: UUID): Room
}

@RestController
class FindRoomControllerImpl(
    private val findUseCase: FindUseCase,
) : FindRoomController {
    override fun find() = findUseCase.find().map(::RoomResponse)

    override fun findDetails(sessionUserId: UUID, roomId: UUID): Room {
        return findUseCase.find(sessionUserId, roomId)
    }
}
