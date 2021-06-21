package dev.gunlog.web;

import dev.gunlog.Model.Game;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    static Game game;

    @MessageMapping("/start")
    @SendTo("/play/start")
    public Game boradCast(){
        game = new Game();
        return game;
    }
    @MessageMapping("/left")
    @SendTo("/play/left")
    public Game leftMove() {
        game.leftMove();
        return game;
    }
    @MessageMapping("/right")
    @SendTo("/play/right")
    public Game rightMove() {
        game.rightMove();
        return game;
    }
}
