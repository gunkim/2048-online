package io.github.gunkim.data

import io.github.gunkim.domain.user.User
import io.github.gunkim.domain.user.UserRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DummyUserRepository(
    private val map: MutableMap<UUID, User> = mutableMapOf()
) : UserRepository {
    override fun find() = map.values.toList()

    override fun find(id: UUID) = map[id] ?: throw IllegalArgumentException("존재하지 않는 유저입니다.")
    override fun findByEmail(email: String): User? {
        return map.values.find { it.email == email }
    }

    override fun save(user: User) = user.also { map[user.id] = user }
}
