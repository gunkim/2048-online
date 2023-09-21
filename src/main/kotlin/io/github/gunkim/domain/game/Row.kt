package io.github.gunkim.domain.game

data class Row(
    val content: List<Cell>
) {
    val score: Int
        get() = totalScore(content)

    val isGameWin: Boolean
        get() = isGameWin(content)

    val isFull: Boolean
        get() = content.all { it != Cell.ZERO }

    init {
        require(content.size == SIZE) { "가로 폭은 ${SIZE}여야 합니다." }
    }

    fun moveLeft() = content
        .removeZero()
        .fold(emptyList(), ::move)
        .let(::fill)
        .let(::Row)

    fun moveRight() = content.reversed()
        .removeZero()
        .fold(emptyList(), ::move)
        .let(::fill)
        .reversed()
        .let(::Row)

    operator fun get(index: Int): Cell = content[index]

    fun addOneCell(x: Int) = Row(content.addLevel1Cell(x))

    private fun move(accumulator: List<Cell>, cell: Cell): List<Cell> {
        return if (accumulator.isNotEmpty()) {
            val lastCell = accumulator.last()

            if (lastCell == cell) {
                accumulator.dropLast(1) + lastCell.levelUp()
            } else {
                accumulator + cell
            }
        } else {
            listOf(cell)
        }
    }

    private fun fill(movedRow: List<Cell>): List<Cell> = if (movedRow.size < SIZE) {
        movedRow + List(SIZE - movedRow.size) { Cell.ZERO }
    } else {
        movedRow
    }

    companion object {
        private val SIZE = 4

        fun empty() = Row(listOf(Cell.ZERO, Cell.ZERO, Cell.ZERO, Cell.ZERO))
    }
}

private fun totalScore(cells: List<Cell>) = cells.sumOf(Cell::value)
private fun isGameWin(cells: List<Cell>) = cells.any(Cell.Companion::isWin)

private fun List<Cell>.removeZero() = filterNot { it == Cell.ZERO }
private fun List<Cell>.addLevel1Cell(posX: Int): List<Cell> {
    val content = this.toMutableList()
    content[posX] = Cell.ONE

    return content
}
