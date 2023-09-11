package io.github.gunkim.data

import io.github.gunkim.domain.score.HighScore
import io.github.gunkim.domain.score.ScoreRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DummyScoreRepository(
    private val map: MutableMap<UUID, HighScore> = mutableMapOf(),
) : ScoreRepository {

    override fun save(score: HighScore) = score
        .also { map[score.id] = score }
        .let { score }

    override fun findByUserId(userId: UUID): HighScore = map[userId] ?: error("존재하지 않는 사용자입니다.")

    override fun findAll(): List<HighScore> = map.values.toList()
}