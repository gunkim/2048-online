package dev.gunlog.multi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRoomRedisRepository extends CrudRepository<GameRoomRedis, Long> {
    Optional<GameRoomRedis> findByPlayersNickname(String nickname);
    List<GameRoomRedis> findAll();
    Optional<GameRoomRedis> findById(Long id);
}
