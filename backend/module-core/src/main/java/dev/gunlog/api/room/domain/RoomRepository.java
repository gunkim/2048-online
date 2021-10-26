package dev.gunlog.api.room.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}