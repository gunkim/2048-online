package io.github.gunkim.game.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class RowsTests {
    @Test
    fun `사이즈가 4가 아닐 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Rows(listOf()) }
            .apply { assertThat(message).isEqualTo("세로 폭은 4여야 합니다.") }
    }

    @Test
    fun `위로 이동`() {
        val rows = Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
            ),
        )

        val (movedRows, isMoved) = rows.moveUp()

        assertAll(
            {
                assertThat(movedRows).isEqualTo(
                    Rows(
                        listOf(
                            Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                            Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                            Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                            Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                        ),
                    ),
                )
            },
            { assertThat(isMoved).isTrue() },
        )
    }

    @Test
    fun `아래로 이동`() {
        val rows = Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
            ),
        )

        val (movedRows, isMoved) = rows.moveDown()

        assertAll(
            {
                assertThat(movedRows).isEqualTo(
                    Rows(
                        listOf(
                            Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                            Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                            Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                            Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                        ),
                    ),
                )
            },
            { assertThat(isMoved).isTrue() },
        )
    }
}
