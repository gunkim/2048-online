package io.github.gunkim.game.domain.vo

private fun totalScore(rows: List<Row>) = rows.sumOf { it.score }
private fun isGameWin(rows: List<Row>) = rows.any { it.isGameWin }

data class Rows(
    val content: List<Row>
) {
    val score: Int
        get() = totalScore(content)

    val isGameWin: Boolean
        get() = isGameWin(content)

    init {
        require(content.size == SIZE) { "세로 폭은 ${SIZE}여야 합니다." }
    }

    fun moveLeft() = Rows(content.map(Row::moveLeft))

    fun moveRight() = Rows(content.map(Row::moveRight))

    fun moveUp(): Rows = rotate().moveLeft().rotate()

    fun moveDown(): Rows = rotate().moveRight().rotate()

    private fun rotate() = Rows(IntRange(0, SIZE - 1).map(::createRow))

    private fun createRow(i: Int) = Row(IntRange(0, SIZE - 1).map { j -> content[j][i] })

    companion object {
        private const val SIZE = 4

        fun empty() = Rows(IntRange(0, SIZE - 1).map { Row.empty() })
    }
}