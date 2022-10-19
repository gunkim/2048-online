package io.github.gunkim.game.domain.vo

data class Rows(
    private val content: List<Row>
) {
    init {
        require(content.size == SIZE) { "세로 폭은 ${SIZE}여야 합니다." }
    }

    fun moveLeft(): Rows {
        val movedRows = content
            .map(Row::moveLeft)
        return Rows(movedRows)
    }

    fun moveRight(): Rows {
        val movedRows = content
            .map(Row::moveRight)
        return Rows(movedRows)
    }

    fun moveUp(): Rows {
        return rotate().moveLeft().rotate()
    }

    fun moveDown(): Rows {
        return rotate().moveRight().rotate()
    }

    private fun rotate(): Rows {
        val main = mutableListOf<Row>()
        for (i in IntRange(0, SIZE - 1)) {
            val list = mutableListOf<Cell>()
            for (j in IntRange(0, SIZE - 1)) {
                val cell = content[j][i]
                list.add(cell)
            }
            main.add(Row(list))
        }
        return Rows(main)
    }

    companion object {
        private val SIZE = 4
    }
}