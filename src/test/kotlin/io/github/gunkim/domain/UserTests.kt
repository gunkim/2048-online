package io.github.gunkim.domain

import io.github.gunkim.domain.user.User
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

@DisplayName("유저는")
class UserTests : StringSpec({
    "이름이 유효하지 않을 경우 예외가 발생한다" {
        assertThrows<IllegalArgumentException> { User(name = "", email = "gunkim.dev@gmail.com") }
            .apply { assertThat(message).isEqualTo("이름이 비어있을 수 없습니다.") }
    }
    "이메일 형식이 유효하지 않을 경우 예외가 발생한다" {
        listOf(
            "gun@com",
            "한글@한글",
            "gun@com@com",
            "gunkim",
        ).forEach {
            assertThrows<IllegalArgumentException> { User(name = "거누", email = it) }
                .apply { assertThat(message).isEqualTo("이메일 형식이 올바르지 않습니다.") }
        }
    }
})
