package io.github.gunkim.game.domain

import java.util.*

interface Rooms {
    fun find(): List<Rooms>
    fun find(id: UUID): Room
    fun save(room: Room): Room
}
