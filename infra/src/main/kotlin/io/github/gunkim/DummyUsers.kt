package io.github.gunkim

import io.github.gunkim.game.domain.User
import io.github.gunkim.game.domain.Users
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DummyUsers(
    private val map: MutableMap<UUID, User> = mutableMapOf()
) : Users {
    override fun find() = map.values.toList()

    override fun find(id: UUID) = map[id] ?: throw IllegalArgumentException("존재하지 않는 유저입니다.")

    override fun save(user: User) = user.also { map[user.id] = user }
}
