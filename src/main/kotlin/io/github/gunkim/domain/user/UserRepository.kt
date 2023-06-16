package io.github.gunkim.domain.user

import java.util.UUID

interface UserRepository {
    fun find(): List<User>
    fun find(id: UUID): User
    fun findByEmail(email: String): User?
    fun save(user: User): User
}
