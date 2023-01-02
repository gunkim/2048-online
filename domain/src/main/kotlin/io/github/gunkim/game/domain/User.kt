package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.Social
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val email: String,
    val social: Social,
    val profileImageUrl: String? = null,
    val role: Role = Role.USER
) {
    fun update(name: String, profileImageUrl: String) = User(
        id,
        name,
        email,
        social,
        profileImageUrl,
        role
    )

    init {
        require(name.isNotBlank()) { "이름이 비어있을 수 없습니다." }
    }
}
