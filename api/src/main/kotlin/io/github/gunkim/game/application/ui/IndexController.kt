package io.github.gunkim.game.application.ui

import io.github.gunkim.game.application.common.SessionUserId
import io.github.gunkim.game.application.usecase.room.JoinUseCase
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

interface IndexController {
    @GetMapping("/")
    fun index(): String

    @GetMapping("/rooms/{roomId}/details")
    fun room(
        @SessionUserId sessionUserId: UUID,
        @PathVariable roomId: UUID,
    ): String
}

@Controller
class IndexControllerImpl(
    private val joinUseCase: JoinUseCase,
) : IndexController {
    override fun index() = "index"

    override fun room(sessionUserId: UUID, roomId: UUID): String {
        joinUseCase.join(roomId, sessionUserId)

        return "room"
    }
}
