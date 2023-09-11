package io.github.gunkim.endpoint.http.score.response

import io.github.gunkim.domain.score.HighScore

data class ChangeScoreResponse(
    val score: Long,
    val message: String
) {
    constructor(highScore: HighScore) : this(highScore.score, "기존 기록을 갱신 했습니다.")
    constructor(score: Long) : this(score, "기존 기록을 갱신하지 못했습니다. 전 최고기록은 $score 입니다.")
}
