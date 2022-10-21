package io.github.gunkim.game.domain

import io.github.gunkim.game.domain.vo.MoveType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RoomTests {
    @Test
    fun `인원이 2명보다 적을 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Room("test", listOf()) }
            .apply { assertThat(message).isEqualTo("게임에 참여할 수 있는 인원은 최소 2명 이상입니다.") }
    }

    @Test
    fun `게임이 시작되지 않았을 경우 예외가 발생한다`() {
        val player1 = Player("깜지")
        val player2 = Player("짱구")

        val boards = listOf(Board.create(player1), Board.create(player2))

        val room = Room("테스트 방", boards)

        assertThrows<IllegalStateException> { room.move(player1, MoveType.TOP) }
            .apply { assertThat(message).isEqualTo("게임이 시작되지 않았습니다.") }
    }

    @Test
    fun `게임이 이미 시작된 상태에서 또 시작을 할 경우 예외가 발생한다`() {
        val player1 = Player("깜지")
        val player2 = Player("짱구")

        val boards = listOf(Board.create(player1), Board.create(player2))

        val room = Room("테스트 방", boards, true)

        assertThrows<IllegalStateException> { room.start(player1) }
            .apply { assertThat(message).isEqualTo("이미 게임이 시작되었습니다.") }
    }

    @Test
    fun `게임이 시작되지 않았는데 종료할 경우 예외가 발생한다`() {
        val player1 = Player("깜지")
        val player2 = Player("짱구")

        val boards = listOf(Board.create(player1), Board.create(player2))

        val room = Room("테스트 방", boards)

        assertThrows<IllegalStateException> { room.stop(player1) }
            .apply { assertThat(message).isEqualTo("게임이 시작되지 않았습니다.") }
    }

    @Test
    fun `방장이 아닌 플레이어가 시작을 할 경우 예외가 발생한다`() {
        val player1 = Player("깜지")
        val player2 = Player("짱구")

        val boards = listOf(Board.create(player1), Board.create(player2))

        val room = Room("테스트 방", boards)

        assertThrows<IllegalArgumentException> { room.start(player2) }
            .apply { assertThat(message).isEqualTo("시작은 방장만 할 수 있습니다.") }
    }

    @Test
    fun `방장이 아닌 플레이어가 종료를 할 경우 예외가 발생한다`() {
        val player1 = Player("깜지")
        val player2 = Player("짱구")

        val boards = listOf(Board.create(player1), Board.create(player2))

        val room = Room("테스트 방", boards, true)

        assertThrows<IllegalArgumentException> { room.stop(player2) }
            .apply { assertThat(message).isEqualTo("종료는 방장만 할 수 있습니다.") }
    }
}