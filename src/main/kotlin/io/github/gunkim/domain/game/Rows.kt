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
    init {
        require(content.size == SIZE) { "세로 폭은 ${SIZE}여야 합니다." }
    }

    val score: Int
        get() = totalScore(content)

    val isGameWin: Boolean
        get() = isGameWin(content)

    val isFull: Boolean
        get() = content.all(Row::isFull)

    fun moveLeft() = content
        .map(Row::moveLeft)
        .let(::Rows)

    fun moveRight() = content
        .map(Row::moveRight)
        .let(::Rows)

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
            if (cell == Cell.ZERO) {
                x to y
            } else {
                null
            }
        }
    }.filterNotNull()

    private fun rotate() = IntRange(0, SIZE - 1)
        .map(::createRow)
        .let(::Rows)

    private fun createRow(i: Int) = IntRange(0, SIZE - 1)
        .map { j -> content[j][i] }
        .let(::Row)

    companion object {
        private const val SIZE = 4

        fun empty() = IntRange(0, SIZE - 1)
            .map { Row.empty() }
            .let(::Rows)
    }
}
