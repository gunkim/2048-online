package io.github.gunkim.domain.game

private fun totalScore(rows: List<Row>) = rows.sumOf { it.score }
private fun isGameWin(rows: List<Row>) = rows.any { it.isGameWin }
private fun List<Row>.addLevel1Cell(posX: Int, posY: Int): List<Row> {
    val content = this.toMutableList()
    content[posY] = content[posY].addOneCell(posX)

    return content
}

data class Rows(
    val content: List<Row>,
) {
    val score: Int
        get() = totalScore(content)

    val isGameWin: Boolean
        get() = isGameWin(content)

    val isFull: Boolean
        get() = content.all(Row::isFull)

    init {
        require(content.size == SIZE) { "세로 폭은 ${SIZE}여야 합니다." }
    }

    fun moveLeft() = Rows(content.map(Row::moveLeft))

    fun moveRight() = Rows(content.map(Row::moveRight))

    fun moveUp() = rotate().moveLeft().rotate()

    fun moveDown() = rotate().moveRight().rotate()

    fun addLevel1CellWithRandomPosition(): Rows {
        if (isFull) return this

        val (posX, posY) = getZeroCellPositions().randomOrNull()
            ?: return this

        return Rows(content.addLevel1Cell(posX, posY))
    }

    operator fun get(index: Int): Row = content[index]

    private fun getZeroCellPositions() = content.flatMapIndexed { y, row ->
        row.content.mapIndexed { x, cell ->
            if (cell == Cell.ZERO) x to y else null
        }
    }.filterNotNull()

    private fun rotate() = Rows(IntRange(0, SIZE - 1).map(::createRow))

    private fun createRow(i: Int) = Row(IntRange(0, SIZE - 1).map { j -> content[j][i] })

    companion object {
        private const val SIZE = 4

        fun empty() = Rows(IntRange(0, SIZE - 1).map { Row.empty() })
    }
}
