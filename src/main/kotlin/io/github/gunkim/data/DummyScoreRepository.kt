package io.github.gunkim.data

import io.github.gunkim.domain.score.ScoreHistory
import io.github.gunkim.domain.score.ScoreRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DummyScoreRepository(
    private val map: MutableMap<UUID, ScoreHistory> = mutableMapOf(),
) : ScoreRepository {

    override fun save(score: ScoreHistory) = score
        .also { map[score.id] = score }
        .let { score }

    override fun findHighScore(userId: UUID): ScoreHistory = map.values
        .filter { it.userId == userId }
        .maxByOrNull { it.score }
        ?: save(ScoreHistory(userId = userId))
}
