package io.github.gunkim.domain.game

import io.github.gunkim.domain.user.User
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("게이머는")
class GamerTests : StringSpec({
    "플레이어를 가지고 있다면 참을 반환한다" {
        val user = User(name = "gunkim", email = "gunkim.dev@gmail.com")
        val gamer = Gamer(user = user, board = Board.create())

        assertTrue(gamer.hasPlayerId(user.id))
    }
    "플레이어를 가지고 있지 않다면 거짓을 반환한다" {
        val user = User(name = "gunkim", email = "gunkim.dev@gmail.com")
        val gamer = Gamer(user = user, board = Board.create())

        val user2 = User(name = "gunkim2", email = "gunkim.dev@gmail.com")
        assertFalse(gamer.hasPlayerId(user2.id))
    }
})
