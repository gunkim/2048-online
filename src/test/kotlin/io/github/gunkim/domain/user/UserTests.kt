package io.github.gunkim.domain.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("유저는")
class UserTests : StringSpec({
    "이름이 유효하지 않을 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { User(name = "", email = "gunkim.dev@gmail.com") }
            .apply { message shouldBe "이름이 비어있을 수 없습니다." }
    }
    "이메일 형식이 유효하지 않을 경우 예외가 발생한다" {
        listOf(
            "gun@com",
            "한글@한글",
            "gun@com@com",
            "gunkim",
        ).forEach {
            shouldThrow<IllegalArgumentException> { User(name = "거누", email = it) }
                .apply { message shouldBe "이메일 형식이 올바르지 않습니다." }
        }
    }
})
