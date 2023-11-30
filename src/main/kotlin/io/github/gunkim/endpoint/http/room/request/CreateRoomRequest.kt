package io.github.gunkim.endpoint.http.room.request

data class CreateRoomRequest(
    val title: String = "",
    val timer: Long = 30L
)
