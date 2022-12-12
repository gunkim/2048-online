package io.github.gunkim.game.domain

import java.util.*

interface Users {
    fun find(): List<User>
    fun find(id: UUID): User
    fun save(user: User): User
}
