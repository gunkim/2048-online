package io.github.gunkim.game.domain

import java.util.*

interface Players {
    fun find(): List<Player>
    fun find(id: UUID): Player
    fun save(player: Player): Player
}
