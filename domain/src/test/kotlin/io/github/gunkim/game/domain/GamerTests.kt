package io.github.gunkim.game.domain

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GamerTests {
    @Test
    fun `플레이어를 가지고 있다면 참을 반환한다`() {
        val user = User(name = "gunkim")
        val gamer = Gamer(user = user, board = Board.create())

        assertTrue(gamer.hasPlayer(user))
    }

    @Test
    fun `플레이어를 가지고 있지 않다면 거짓을 반환한다`() {
        val user = User(name = "gunkim")
        val gamer = Gamer(user = user, board = Board.create())

        val user2 = User(name = "gunkim2")
        assertFalse(gamer.hasPlayer(user2))
    }
}
