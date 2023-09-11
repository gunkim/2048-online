package io.github.gunkim.endpoint.http.score.request

import java.util.UUID

data class ChangeScoreRequest(
    val userId: UUID,
    val newScore: Long,
)
