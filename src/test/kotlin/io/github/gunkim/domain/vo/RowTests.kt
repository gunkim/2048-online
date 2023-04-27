package io.github.gunkim.domain.vo

import io.github.gunkim.domain.game.Cell
import io.github.gunkim.domain.game.Row
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

@DisplayName("가로 줄은")
class RowTests : StringSpec({
    "사이즈가 4가 아닐 경우 예외가 발생한다" {
        assertThrows<IllegalArgumentException> { Row(listOf()) }
            .apply { assertThat(message).isEqualTo("가로 폭은 4여야 합니다.") }
    }
    "경로상 같은 셀이 있다면 왼쪽 이동하면서 합쳐진다" {
        val row = Row(listOf(Cell.ONE, Cell.ONE, Cell.ONE, Cell.ONE))

        val movedRow = row.moveLeft()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.TWO, Cell.TWO, Cell.ZERO, Cell.ZERO)))
    }
    "경로상 같은 셀이 없다면 왼쪽 이동한다" {
        val row = Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ONE))

        val movedRow = row.moveLeft()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ONE, Cell.ZERO, Cell.ZERO, Cell.ZERO)))
    }
    "경로상 같은 셀이 있다면 오른쪽 이동하면서 합쳐진다" {
        val row = Row(listOf(Cell.ONE, Cell.ONE, Cell.ONE, Cell.ONE))

        val movedRow = row.moveRight()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ZERO, Cell.ZERO, Cell.TWO, Cell.TWO)))
    }
    "경로상 같은 셀이 없다면 오른쪽 이동한다" {
        val row = Row(listOf(Cell.ONE, Cell.ZERO, Cell.ZERO, Cell.ZERO))

        val movedRow = row.moveRight()

        assertThat(movedRow).isEqualTo(Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ONE)))
    }
    "특정 위치에 Level1 Cell을 추가한다" {
        val row = Row.empty()

        val addedRow = row.addOneCell(0)

        assertThat(addedRow).isEqualTo(Row(listOf(Cell.ONE, Cell.ZERO, Cell.ZERO, Cell.ZERO)))
    }
})
