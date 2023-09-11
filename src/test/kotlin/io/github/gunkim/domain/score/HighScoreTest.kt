package io.github.gunkim.domain.score

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

@DisplayName("최고 점수는")
class HighScoreTest : StringSpec({
    "0 미만의 점수가 입력되면 에러가 발생한다." {
        val highScore = HighScore(
            userId = UUID.randomUUID(),
        )

        shouldThrow<IllegalArgumentException> {
            HighScore(
                userId = UUID.randomUUID(),
                score = -1
            )
        }.apply { message shouldBe "점수는 0 미만이 될 수 없습니다." }


        shouldThrow<IllegalArgumentException> {
            highScore.changeRecord(-1)
        }.apply { message shouldBe "점수는 0 미만이 될 수 없습니다." }
    }


    "새로 입력한 점수가 원래 점수보다 높으면 true를 반환한다." {
        val highScore = HighScore(
            userId = UUID.randomUUID(),
            score = 2048
        )

        highScore.isHigh(2049) shouldBe true
    }

})
