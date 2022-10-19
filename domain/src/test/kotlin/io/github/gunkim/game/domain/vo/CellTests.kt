package io.github.gunkim.game.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CellTests {
    @Test
    fun `최고 레벨이 레벨업을 할 경우 예외가 발생한다`() {
        val cell = Cell.TWELVE

        assertThrows<IllegalStateException> { cell.merge(Cell.TWELVE) }
            .apply { assertThat(message).isEqualTo("이미 최고 레벨이므로 레벨업이 불가능합니다.") }
    }
}