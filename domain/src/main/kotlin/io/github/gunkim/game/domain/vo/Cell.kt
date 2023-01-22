package io.github.gunkim.game.domain.vo

enum class Cell(
    val value: Int,
) {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(4),
    FOUR(8),
    FIVE(16),
    SIX(32),
    SEVEN(64),
    EIGHT(128),
    NINE(256),
    TEN(512),
    ELEVEN(1024),
    TWELVE(2048),
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
        ELEVEN -> TWELVE
        TWELVE -> error("이미 최고 레벨이므로 레벨업이 불가능합니다.")
    }

    companion object {
        fun isWin(cell: Cell) = cell == TWELVE
    }
}
