package io.github.gunkim.game.domain

import java.util.*

interface Rooms {
    fun find(): List<Room>
    fun find(id: UUID): Room
    fun existByUserId(userId: UUID): Boolean
    fun save(room: Room): Room
}
