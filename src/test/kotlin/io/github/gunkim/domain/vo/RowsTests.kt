package io.github.gunkim.domain.vo

import io.github.gunkim.game.domain.vo.Cell
import io.github.gunkim.game.domain.vo.Row
import io.github.gunkim.game.domain.vo.Rows
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

@DisplayName("행들은")
class RowsTests : StringSpec({
    "사이즈가 4가 아닐 경우 예외가 발생한다" {
        assertThrows<IllegalArgumentException> { Rows(listOf()) }
            .apply { assertThat(message).isEqualTo("세로 폭은 4여야 합니다.") }
    }
    "위로 이동한다" {
        val rows = Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
            ),
        )

        val movedRows = rows.moveUp()

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
        )
    }
    "아래로 이동한다" {
        val rows = Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
            ),
        )

        val movedRows = rows.moveDown()

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
        )
    }
})
