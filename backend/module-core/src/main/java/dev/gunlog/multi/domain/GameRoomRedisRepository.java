package dev.gunlog.multi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRedisRepository extends CrudRepository<GameRoomRedis, Long> {
}
