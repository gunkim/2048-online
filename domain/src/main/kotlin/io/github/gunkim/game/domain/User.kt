package io.github.gunkim.game.domain

import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val profileImageUrl: String? = null
) {
    init {
        require(name.isNotBlank()) { "이름이 비어있을 수 없습니다." }
    }
}
