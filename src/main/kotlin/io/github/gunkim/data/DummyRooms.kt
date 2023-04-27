package io.github.gunkim.data

import io.github.gunkim.domain.room.Room
import io.github.gunkim.domain.room.Rooms
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DummyRooms(
    val map: MutableMap<UUID, Room> = mutableMapOf(),
) : Rooms {
    override fun find() = map.values.toList()

    override fun find(id: UUID) = map[id] ?: throw IllegalArgumentException("존재하지 않는 방입니다.")
    override fun existByUserId(userId: UUID) = map.values.any { it.hasUserId(userId) }
    override fun save(room: Room) = room.also { map[room.id] = room }
    override fun delete(room: Room) {
        map.remove(room.id)
    }
}
