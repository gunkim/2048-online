//package dev.gunlog.single.service;
//
//import dev.gunlog.multi.model.Game;
//import dev.gunlog.single.domain.GameRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class SingleService {
//    private final GameRepository gameRepository;
//
//    public Game leftMove(String username) {
//        Game game = gameRepository.findGameByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("게임 정보가 없습니다."));
//        game.leftMove();
//        return game;
//    }
//    public Game rightMove(String username) {
//        Game game = gameRepository.findGameByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("게임 정보가 없습니다."));
//        game.rightMove();
//        return game;
//    }
//    public Game topMove(String username) {
//        Game game = gameRepository.findGameByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("게임 정보가 없습니다."));
//        game.topMove();
//        return game;
//    }
//    public Game bottomMove(String username) {
//        Game game = gameRepository.findGameByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("게임 정보가 없습니다."));
//        game.bottomMove();
//        return game;
//    }
//    public Game initGame(String username) {
//        Game game = new Game();
//        gameRepository.save(username, game);
//        return game;
//    }
//}