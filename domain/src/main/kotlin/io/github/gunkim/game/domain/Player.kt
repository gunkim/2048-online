package io.github.gunkim.game.domain

data class Player(
    val name: String,
    val profileImageUrl: String? = null
) {
    init {
        require(name.isNotBlank()) { "이름이 비어있을 수 없습니다." }
    }
}