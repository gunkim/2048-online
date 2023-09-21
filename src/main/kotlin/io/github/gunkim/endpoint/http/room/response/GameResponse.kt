package io.github.gunkim.endpoint.http.room.response

import io.github.gunkim.domain.game.Cell
import io.github.gunkim.domain.game.Gamer
import io.github.gunkim.domain.game.Row
import java.util.*

data class GameResponse(
    val userId: UUID,
    val name: String,
    val score: Int,
    val board: List<List<String>>
) {
    constructor(gamer: Gamer) : this(
        gamer.user.id,
        gamer.user.name,
        gamer.score,
        gamer.board.rows.content
            .map(Row::content)
            .map { row -> row.map(Cell::name) }
    )
}
