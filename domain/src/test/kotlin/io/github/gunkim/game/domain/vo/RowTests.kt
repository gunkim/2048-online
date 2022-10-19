package io.github.gunkim.game.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RowTests {
    @Test
    fun `사이즈가 4가 아닐 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Row(listOf()) }
            .apply { assertThat(message).isEqualTo("가로 폭은 4여야 합니다.") }
    }

    @Test
    fun `왼쪽으로 이동한다`() {
        val row = Row(listOf(Cell.ONE, Cell.ONE, Cell.ONE, Cell.ONE))
        val movedRow = row.moveLeft()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.TWO, Cell.TWO, Cell.ZERO, Cell.ZERO)))
    }

    @Test
    fun `오른쪽으로 이동한다`() {
        val row = Row(listOf(Cell.ONE, Cell.ONE, Cell.ONE, Cell.ONE))
        val movedRow = row.moveRight()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ZERO, Cell.ZERO, Cell.TWO, Cell.TWO)))
    }
}