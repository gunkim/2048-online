package dev.gunlog.socket;

import dev.gunlog.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameController {
    private final RedisTemplate<String, Player> template;

    @MessageMapping("/start")
    @SendTo("/play/start")
    public Player boradCast(){
        template.opsForValue().set("game", new Player());
        return template.opsForValue().get("game");
    }
    @MessageMapping("/left")
    @SendTo("/play/left")
    public Player leftMove() {
        System.out.println("왼");
        Player player = template.opsForValue().get("game");
        player.leftMove();
        template.opsForValue().set("game", player);
        return template.opsForValue().get("game");
    }
    @MessageMapping("/right")
    @SendTo("/play/right")
    public Player rightMove() {
        System.out.println("우");
        Player player = template.opsForValue().get("game");
        player.rightMove();
        template.opsForValue().set("game", player);
        return template.opsForValue().get("game");
    }
    @MessageMapping("/top")
    @SendTo("/play/top")
    public Player topMove() {
        System.out.println("상");
        Player player = template.opsForValue().get("game");
        player.topMove();
        template.opsForValue().set("game", player);
        return template.opsForValue().get("game");
    }
    @MessageMapping("/bottom")
    @SendTo("/play/bottom")
    public Player bottomMove() {
        System.out.println("하");
        Player player = template.opsForValue().get("game");
        player.bottomMove();
        template.opsForValue().set("game", player);
        return template.opsForValue().get("game");
    }
}
