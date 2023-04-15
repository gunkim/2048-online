package io.github.gunkim.game.application.endpoint.http.room

import io.github.gunkim.game.application.common.id
import io.github.gunkim.game.application.endpoint.http.room.response.RoomResponse
import io.github.gunkim.game.application.endpoint.http.room.response.WaitRoomResponse
import io.github.gunkim.game.application.usecase.room.FindUseCase
import io.github.gunkim.game.domain.Room
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class FindRoomController(
    private val findUseCase: FindUseCase,
) {
    @GetMapping("/rooms")
    fun find() = findUseCase.find().map(::RoomResponse)

    @GetMapping("/rooms/{roomId}")
    fun findDetails(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID): Room {
        return findUseCase.find(user.id, roomId)
    }

    @GetMapping("/rooms/{roomId}/wait")
    fun findWaitRoom(user: OAuth2AuthenticationToken, @PathVariable roomId: UUID) =
        WaitRoomResponse(findUseCase.find(user.id, roomId))
}
