package io.github.gunkim.game.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTests {
    @Test
    fun `이름이 유효하지 않을 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { User(name = "") }
            .apply { assertThat(message).isEqualTo("이름이 비어있을 수 없습니다.") }
    }
}
