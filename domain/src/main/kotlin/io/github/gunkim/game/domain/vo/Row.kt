package io.github.gunkim.game.domain.vo

data class Row(
    private val content: List<Cell>
) {
    init {
        require(content.size == SIZE) { "가로 폭은 ${SIZE}여야 합니다." }
    }

    fun moveLeft() = Row(
        content
            .fold(emptyList(), ::move)
            .let(::fill)
    )

    fun moveRight() = Row(
        content
            .reversed()
            .fold(emptyList(), ::move)
            .let(::fill)
            .reversed()
    )

    private fun fill(movedRow: List<Cell>): List<Cell> = if (movedRow.size < SIZE) {
        movedRow + List(SIZE - movedRow.size) { Cell.ZERO }
    } else {
        movedRow
    }

    operator fun get(index: Int): Cell = content[index]

    private fun move(accumulator: List<Cell>, cell: Cell): List<Cell> = if (accumulator.isEmpty()) {
        listOf(cell)
    } else {
        val lastCell: Cell = accumulator.last()

        if (lastCell == cell) {
            accumulator.dropLast(1) + lastCell.merge(cell)
        } else {
            accumulator + cell
        }
    }

    companion object {
        private val SIZE = 4
    }
}