package io.github.gunkim.application.spring.endpoint.http.room

import io.github.gunkim.application.spring.common.id
import io.github.gunkim.application.spring.endpoint.http.room.response.RoomResponse
import io.github.gunkim.application.spring.endpoint.http.room.response.WaitRoomResponse
import io.github.gunkim.application.FindRoom
import io.github.gunkim.domain.Room
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

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

    @GetMapping("/rooms/{roomId}/wait")
    fun findWaitRoom(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        WaitRoomResponse(findRoom.find(user.id, roomId))
}
