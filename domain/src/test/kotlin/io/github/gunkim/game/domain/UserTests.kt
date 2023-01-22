package io.github.gunkim.game.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UserTests {
    @Test
    fun `이름이 유효하지 않을 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { User(name = "", email = "gunkim.dev@gmail.com") }
            .apply { assertThat(message).isEqualTo("이름이 비어있을 수 없습니다.") }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "gun@com",
            "한글@한글",
            "gun@com@com",
            "gunkim",
        ],
    )
    fun `이메일 형식이 유효하지 않을 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { User(name = "거누", email = "gun@com") }
            .apply { assertThat(message).isEqualTo("이메일 형식이 올바르지 않습니다.") }
    }
}
