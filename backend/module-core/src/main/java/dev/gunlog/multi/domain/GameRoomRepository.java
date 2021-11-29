package dev.gunlog.multi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRoomRepository extends CrudRepository<GameRoom, Long> {
    Optional<GameRoom> findByPlayersNickname(String nickname);
    List<GameRoom> findAll();
    Optional<GameRoom> findById(Long id);
}
