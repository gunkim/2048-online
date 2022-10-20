package io.github.gunkim.game.domain.vo

data class Rows(
    private val content: List<Row>
) {
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
    }
}