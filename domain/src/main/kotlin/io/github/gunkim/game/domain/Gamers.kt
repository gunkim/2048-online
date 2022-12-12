package io.github.gunkim.game.domain

import java.util.*

interface Gamers {
    fun find(): List<Gamer>
    fun find(id: UUID): Gamer
    fun findByUserId(userId: UUID): Gamer
    fun save(gamer: Gamer): Gamer
}
