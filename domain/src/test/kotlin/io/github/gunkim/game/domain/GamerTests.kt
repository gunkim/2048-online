package io.github.gunkim.game.domain

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GamerTests {
    @Test
    fun `플레이어를 가지고 있다면 참을 반환한다`() {
        val player = Player("gunkim")
        val gamer = Gamer(player, Board.create(player))

        assertTrue(gamer.hasPlayer(player))
    }

    @Test
    fun `플레이어를 가지고 있지 않다면 거짓을 반환한다`() {
        val player = Player("gunkim")
        val gamer = Gamer(player, Board.create(player))

        val player2 = Player("gunkim2")
        assertFalse(gamer.hasPlayer(player2))
    }
}