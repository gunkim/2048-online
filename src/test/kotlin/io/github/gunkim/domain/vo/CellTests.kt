package io.github.gunkim.domain.vo

import io.github.gunkim.domain.game.Cell
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

@DisplayName("셀은")
class CellTests : StringSpec({
    "최고 레벨이 레벨업을 할 경우 예외가 발생한다" {
        val cell = Cell.ELEVEN

        assertThrows<IllegalStateException> { cell.levelUp() }
            .apply { assertThat(message).isEqualTo("이미 최고 레벨이므로 레벨업이 불가능합니다.") }
    }
})
