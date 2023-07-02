package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.RoomService
import io.github.gunkim.domain.room.Room
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.response.GameResponse
import io.github.gunkim.endpoint.http.room.response.InitGameResponse
import io.github.gunkim.endpoint.http.room.response.RoomResponse
import io.github.gunkim.endpoint.http.room.response.WaitRoomResponse
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/rooms")
class RoomFindController(
    private val roomService: RoomService,
) {
    @GetMapping
    fun find() = roomService.find().map(::RoomResponse)

    @GetMapping("/{roomId}")
    fun findDetails(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID): Room {
        return roomService.find(user.id, roomId)
    }

    @GetMapping("/{roomId}/games")
    fun findGame(@PathVariable roomId: UUID): InitGameResponse {
        val room = roomService.find(roomId)
        val gameResponse = room.gamers
            .map(::GameResponse)

        return InitGameResponse(room.endedAt!!, gameResponse)
    }

    @GetMapping("/{roomId}/wait")
    fun findWaitRoom(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        WaitRoomResponse(roomService.find(user.id, roomId))
}
