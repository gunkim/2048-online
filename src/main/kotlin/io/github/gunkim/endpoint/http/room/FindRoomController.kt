package io.github.gunkim.endpoint.http.room

import io.github.gunkim.application.room.FindRoom
import io.github.gunkim.domain.room.Room
import io.github.gunkim.endpoint.common.id
import io.github.gunkim.endpoint.http.room.response.GameResponse
import io.github.gunkim.endpoint.http.room.response.RoomResponse
import io.github.gunkim.endpoint.http.room.response.WaitRoomResponse
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class FindRoomController(
    private val findRoom: FindRoom,
) {
    @GetMapping("/rooms")
    fun find() = findRoom.find().map(::RoomResponse)

    @GetMapping("/rooms/{roomId}")
    fun findDetails(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID): Room {
        return findRoom.find(user.id, roomId)
    }

    @GetMapping("/rooms/{roomId}/games")
    fun findGame(@PathVariable roomId: UUID) = findRoom.find(roomId)
        .gamers
        .map(::GameResponse)

    @GetMapping("/rooms/{roomId}/wait")
    fun findWaitRoom(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        WaitRoomResponse(findRoom.find(user.id, roomId))
}
