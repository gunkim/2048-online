package io.github.gunkim.domain.game

import java.util.UUID

interface Gamers {
    fun find(): List<Gamer>
    fun find(id: UUID): Gamer
    fun findByUserId(userId: UUID): Gamer
    fun save(gamer: Gamer): Gamer
}
