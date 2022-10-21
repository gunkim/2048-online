package io.github.gunkim.game.domain.vo

private fun totalScore(cells: List<Cell>) = cells.sumOf(Cell::value)
private fun isGameWin(cells: List<Cell>) = cells.any(Cell.Companion::isWin)

data class Row(
    val content: List<Cell>
) {
    val score: Int
        get() = totalScore(content)

    val isGameWin: Boolean
        get() = isGameWin(content)

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

        fun empty() = Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO))
    }
}