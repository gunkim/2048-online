package io.github.gunkim.game.application.socket

import io.github.gunkim.game.application.socket.room.FindRoomResponse
import io.github.gunkim.game.application.usecase.room.FindUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(
    private val findRoomUseCase: FindUseCase
) {
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("rooms", roomResponses())

        return "index"
    }

    private fun roomResponses() = findRoomUseCase.find()
        .map(::FindRoomResponse)
}
