package io.github.gunkim.domain.game

enum class Cell(
    val value: Int,
) {
    ZERO(0),
    ONE(2),
    TWO(4),
    THREE(8),
    FOUR(16),
    FIVE(32),
    SIX(64),
    SEVEN(128),
    EIGHT(256),
    NINE(512),
    TEN(1024),
    ELEVEN(2048),
    ;

    fun levelUp(): Cell = when (this) {
        ZERO -> ONE
        ONE -> TWO
        TWO -> THREE
        THREE -> FOUR
        FOUR -> FIVE
        FIVE -> SIX
        SIX -> SEVEN
        SEVEN -> EIGHT
        EIGHT -> NINE
        NINE -> TEN
        TEN -> ELEVEN
        ELEVEN -> error("이미 최고 레벨이므로 레벨업이 불가능합니다.")
    }

    companion object {
        fun isWin(cell: Cell) = cell == ELEVEN
    }
}
