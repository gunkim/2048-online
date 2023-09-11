package io.github.gunkim.endpoint.http.score

import io.github.gunkim.application.ScoreService
import io.github.gunkim.endpoint.http.score.request.ChangeScoreRequest
import io.github.gunkim.endpoint.http.score.response.ChangeScoreResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ScoreController(
    private val scoreService: ScoreService,
) {

    @PostMapping("/score/change")
    fun change(@RequestBody changeScoreRequest: ChangeScoreRequest) =
        if (scoreService.checkHighScore(changeScoreRequest.newScore, changeScoreRequest.userId)) {
            ChangeScoreResponse(scoreService.changeHighScore(changeScoreRequest.newScore, changeScoreRequest.userId))
        } else {
            ChangeScoreResponse(scoreService.getHighScore(changeScoreRequest.userId).score)
        }
}