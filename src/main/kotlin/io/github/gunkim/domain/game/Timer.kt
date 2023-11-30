package io.github.gunkim.domain.game

enum class Timer(
    private val time: Int
) {
    THIRTY(30),
    SIXTY(60),
    NINETY(90)
    ;

    companion object {
        fun getAllTimers() = Timer.entries
            .map { it.time }
            .toList()
    }
}
