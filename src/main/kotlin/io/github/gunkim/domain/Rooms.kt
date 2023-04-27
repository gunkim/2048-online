package io.github.gunkim.domain

import java.util.UUID

interface Rooms {
    fun find(): List<Room>
    fun find(id: UUID): Room
    fun existByUserId(userId: UUID): Boolean
    fun save(room: Room): Room
    fun delete(room: Room)
}
