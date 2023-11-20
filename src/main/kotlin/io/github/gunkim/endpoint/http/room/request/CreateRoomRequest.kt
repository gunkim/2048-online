package io.github.gunkim.endpoint.http.room.request

data class CreateRoomRequest(
    val title: String = "",
    val playTime: Long = 30L
)
