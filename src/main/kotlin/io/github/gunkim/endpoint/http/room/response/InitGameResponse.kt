package io.github.gunkim.endpoint.http.room.response

import java.time.LocalDateTime

data class InitGameResponse(
    val endTime: LocalDateTime,
    val gamers: List<GameResponse>,
)
