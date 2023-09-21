package io.github.gunkim.domain.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("행들은")
class RowsTests : StringSpec({
    "사이즈가 4가 아닐 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { Rows(listOf()) }
            .apply { message shouldBe "세로 폭은 4여야 합니다." }
    }
    "위로 이동한다" {
        val rows = Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE))
            )
        )

        val movedRows = rows.moveUp()

        movedRows shouldBe Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO))
            )
        )
    }
    "아래로 이동한다" {
        val rows = Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE)),
                Row(listOf(Cell.ZERO, Cell.ONE, Cell.ONE, Cell.THREE))
            )
        )

        val movedRows = rows.moveDown()

        movedRows shouldBe Rows(
            listOf(
                Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO)),
                Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR)),
                Row(listOf(Cell.ZERO, Cell.TWO, Cell.TWO, Cell.FOUR))
            )
        )
    }
})
