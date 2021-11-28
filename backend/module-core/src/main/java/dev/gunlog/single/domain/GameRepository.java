//package dev.gunlog.single.domain;
//
//import dev.gunlog.multi.model.Game;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//public class GameRepository {
//    private static final Map<String, Game> gameMap = new HashMap<>();
//
//    public void save(String username, Game game) {
//        gameMap.put(username, game);
//    }
//    public Optional<Game> findGameByUsername(String username) {
//        return Optional.ofNullable(gameMap.get(username));
//    }
//}