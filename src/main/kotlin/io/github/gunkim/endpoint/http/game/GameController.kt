package io.github.gunkim.endpoint.http.game

import io.github.gunkim.domain.game.Timer
import io.github.gunkim.endpoint.http.game.response.TimerResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
class GameController {

    @GetMapping("/timer")
    fun timer() = Timer.getAllTimers()
        .map { TimerResponse(it) }
        .toList()
}