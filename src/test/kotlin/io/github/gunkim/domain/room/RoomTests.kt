package io.github.gunkim.domain.room

import io.github.gunkim.domain.game.Board
import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.game.MoveType
import io.github.gunkim.domain.user.User
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows

@DisplayName("게임 방은")
class RoomTests : StringSpec({
    "인원이 2명보다 적을 경우 예외가 발생한다" {
        assertThrows<IllegalArgumentException> { Room.stop("test", listOf()) }
            .apply { assertThat(message).isEqualTo("게임에 참여할 수 있는 인원은 최소 1명 이상입니다.") }
    }
    "게임이 시작되지 않았을 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create()),
        )

        val room = Room.stop("테스트 방", gamers)

        assertThrows<IllegalStateException> { room.move(user1, MoveType.TOP) }
            .apply { assertThat(message).isEqualTo("게임이 시작되지 않았습니다.") }
    }
    "게임이 이미 시작된 상태에서 또 시작을 할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create()),
        )

        val room = Room.start("테스트 방", gamers)

        assertThrows<IllegalStateException> { room.start(user1) }
            .apply { assertThat(message).isEqualTo("이미 게임이 시작되었습니다.") }
    }
    "게임이 시작되지 않았는데 종료할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create()),
        )

        val room = Room.stop("테스트 방", gamers)

        assertThrows<IllegalStateException> { room.stop(user1) }
            .apply { assertThat(message).isEqualTo("게임이 시작되지 않았습니다.") }
    }
    "방장이 아닌 플레이어가 시작을 할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create()),
        )

        val room = Room.stop("테스트 방", gamers)

        assertThrows<IllegalArgumentException> { room.start(user2) }
            .apply { assertThat(message).isEqualTo("시작은 방장만 할 수 있습니다.") }
    }
    "방장이 아닌 플레이어가 종료를 할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create()),
        )

        val room = Room.start("테스트 방", gamers)

        assertThrows<IllegalArgumentException> { room.stop(user2) }
            .apply { assertThat(message).isEqualTo("종료는 방장만 할 수 있습니다.") }
    }
    "방장은 게임을 시작할 수 있다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create(), isHost = true, isReady = true),
            Gamer(user = user2, board = Board.create(), isReady = true),
        )

        val room = Room.stop("테스트 방", gamers)

        val result = room.start(user1)

        assertTrue(result.isStart)
    }
    "방장은 게임을 종료할 수 있다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create(), isHost = true),
            Gamer(user = user2, board = Board.create()),
        )

        val room = Room.start("테스트 방", gamers)

        val result = room.stop(user1)

        assertFalse(result.isStart)
    }
})
