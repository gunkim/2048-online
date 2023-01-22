package io.github.gunkim.game.domain.vo

private fun totalScore(rows: List<Row>) = rows.sumOf { it.score }
private fun isGameWin(rows: List<Row>) = rows.any { it.isGameWin }

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

    fun moveLeft(): Pair<Rows, Boolean> {
        val content = content.map(Row::moveLeft)
        val rows: List<Row> = content.map { it.first }

        return Rows(rows) to content.any { it.second }
    }

    fun moveRight(): Pair<Rows, Boolean> {
        val content = content.map(Row::moveRight)
        val rows: List<Row> = content.map { it.first }

        return Rows(rows) to content.any { it.second }
    }

    fun moveUp(): Pair<Rows, Boolean> {
        val content = rotate().moveLeft()

        return content.first.rotate() to content.second
    }

    fun moveDown(): Pair<Rows, Boolean> {
        val content = rotate().moveRight()

        return content.first.rotate() to content.second
    }

    fun init(x: Int, y: Int): Rows {
        val rows = content.toMutableList()
        rows[y] = content[y].init(x)

        return Rows(rows)
    }

    operator fun get(index: Int): Row = content[index]
    private fun rotate() = Rows(IntRange(0, SIZE - 1).map(::createRow))

    private fun createRow(i: Int) = Row(IntRange(0, SIZE - 1).map { j -> content[j][i] })

    companion object {
        private const val SIZE = 4

        fun empty() = Rows(IntRange(0, SIZE - 1).map { Row.empty() })
    }
}
