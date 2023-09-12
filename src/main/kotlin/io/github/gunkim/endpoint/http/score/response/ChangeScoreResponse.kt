package io.github.gunkim.endpoint.http.score.response

import io.github.gunkim.domain.score.HighScore
import io.github.gunkim.endpoint.http.score.response.ChangeScoreStatus.NOT_UPDATED
import io.github.gunkim.endpoint.http.score.response.ChangeScoreStatus.UPDATED

data class ChangeScoreResponse(
    val score: Long,
    val changeScoreStatus: ChangeScoreStatus,
) {
    constructor(highScore: HighScore) : this(highScore.score, UPDATED)
    constructor(score: Long) : this(score, NOT_UPDATED)
}
