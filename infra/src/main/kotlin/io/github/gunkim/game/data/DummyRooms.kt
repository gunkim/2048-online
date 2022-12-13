package io.github.gunkim.game.data

import io.github.gunkim.game.domain.Room
import io.github.gunkim.game.domain.Rooms
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DummyRooms(
    private val map: MutableMap<UUID, Room> = mutableMapOf()
) : Rooms {
    override fun find() = map.values.toList()

    override fun find(id: UUID) = map[id] ?: throw IllegalArgumentException("존재하지 않는 방입니다.")

    override fun save(room: Room) = room.also { map[room.id] = room }
}
