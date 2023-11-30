package io.github.gunkim.domain.room

import io.github.gunkim.domain.game.Board
import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.user.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

@DisplayName("게임 방은")
class RoomTests : StringSpec({
    "인원이 2명보다 적을 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> {
            Room(
                title = "test",
                gamers = listOf(),
                isStart = false,
                playTime = 30L
            )
        }
            .apply { message shouldBe "게임에 참여할 수 있는 인원은 최소 1명 이상입니다." }
    }
    "게임이 이미 시작된 상태에서 또 시작을 할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create())
        )

        val room = Room(title = "테스트 방", gamers = gamers, isStart = true, playTime = 30L)

        shouldThrow<IllegalStateException> { room.start(user1.id) }
            .apply { message shouldBe "이미 게임이 시작되었습니다." }
    }
    "게임이 시작되지 않았는데 종료할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create())
        )

        val room = Room(title = "테스트 방", gamers = gamers, isStart = false, playTime = 30L)

        shouldThrow<IllegalStateException> { room.stop() }
            .apply { message shouldBe "게임이 시작되지 않았습니다." }
    }
    "방장이 아닌 플레이어가 시작을 할 경우 예외가 발생한다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create()),
            Gamer(user = user2, board = Board.create())
        )

        val room = Room(title = "테스트 방", gamers = gamers, isStart = false, playTime = 30L)

        assertThrows<IllegalArgumentException> { room.start(user2.id) }
            .apply { assertThat(message).isEqualTo("시작은 방장만 할 수 있습니다.") }
    }
    "방장은 게임을 시작할 수 있다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create(), isHost = true, isReady = true),
            Gamer(user = user2, board = Board.create(), isReady = true)
        )

        val room = Room(
            title = "테스트 방",
            gamers = gamers,
            isStart = false,
            playTime = 30L,
            gameStopNotifier = { _, _ ->
                println("게임 종료 알림")
            }
        )
        val result = room.start(user1.id)

        result.isStart shouldBe true
    }
    "방장은 게임 종료 알림이 등록 되어 있지 않으면 에러가 발생한다." {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create(), isHost = true, isReady = true),
            Gamer(user = user2, board = Board.create(), isReady = true)
        )

        val room = Room(title = "테스트 방", gamers = gamers, isStart = false, playTime = 30L)

        assertThrows<IllegalStateException> { room.start(user1.id) }
            .apply { assertThat(message).isEqualTo("게임 종료 알림이 등록되어 있지 않습니다.") }
    }
    "방장은 게임을 종료할 수 있다" {
        val user1 = User(name = "깜지", email = "gunkim.dev@gmail.com")
        val user2 = User(name = "짱구", email = "gunkim.dev@gmail.com")

        val gamers = listOf(
            Gamer(user = user1, board = Board.create(), isHost = true),
            Gamer(user = user2, board = Board.create())
        )

        val room = Room(title = "테스트 방", gamers = gamers, isStart = true, playTime = 30L)
        val result = room.stop()

        result.isStart shouldBe false
    }
})
