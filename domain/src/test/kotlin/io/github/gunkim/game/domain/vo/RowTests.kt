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
    fun `경로상 같은 셀이 있다면 왼쪽 이동하면서 합쳐진다`() {
        val row = Row(listOf(Cell.ONE, Cell.ONE, Cell.ONE, Cell.ONE))

        val movedRow = row.moveLeft()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.TWO, Cell.TWO, Cell.ZERO, Cell.ZERO)))
    }

    @Test
    fun `경로상 같은 셀이 없다면 왼쪽 이동한다`() {
        val row = Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ONE))

        val movedRow = row.moveLeft()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ONE, Cell.ZERO, Cell.ZERO, Cell.ZERO)))
    }

    @Test
    fun `경로상 같은 셀이 있다면 오른쪽 이동하면서 합쳐진다`() {
        val row = Row(listOf(Cell.ONE, Cell.ONE, Cell.ONE, Cell.ONE))

        val movedRow = row.moveRight()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ZERO, Cell.ZERO, Cell.TWO, Cell.TWO)))
    }

    @Test
    fun `경로상 같은 셀이 없다면 오른쪽 이동한다`() {
        val row = Row(listOf(Cell.ONE, Cell.ZERO, Cell.ZERO, Cell.ZERO))

        val movedRow = row.moveRight()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ONE)))
    }

    @Test
    fun test() {
        val row = Row(listOf(Cell.TWO, Cell.TWO, Cell.TWO, Cell.TWO))

        val movedRow = row.moveRight()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ZERO, Cell.ZERO, Cell.THREE, Cell.THREE)))
    }
}
