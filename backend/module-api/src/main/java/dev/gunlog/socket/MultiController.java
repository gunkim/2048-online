package dev.gunlog.socket;

import dev.gunlog.multi.domain.GameRoomRedis;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.multi.model.GameRoom;
import dev.gunlog.multi.service.MultiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MultiController {
    private final MultiService multiService;
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/multi/start")
    public void gameStart(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.gameStart(nickname);

        if(room != null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);
            messageTemplate.convertAndSend("/sub/room/"+room.getId()+"/start", room.getStartTime());

            if(room.getGameMode() == Mode.TIME_ATTACK) {
                Thread thread = new TimerThread(room.getId(), multiService, messageTemplate);
                thread.run();
            }
        }
    }
    @MessageMapping("/multi/ready")
    public void gameReady(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.ready(nickname);

        if(room != null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);
        }
    }

    @MessageMapping("/multi/init")
    public void getRoomInfo(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.findRoomByNickname(nickname);

        messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);
        if(room.getStartTime() != null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId()+"/start", room.getStartTime());
        }
    }

    @MessageMapping("/multi/left")
    public void leftMove(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.leftMove(nickname);

        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId()+"/start", "");
        }
        messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);
    }

    @MessageMapping("/multi/right")
    public void rightMove(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.rightMove(nickname);
        messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);

        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId()+"/start", "");
        }
    }

    @MessageMapping("/multi/top")
    public void topMove(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.topMove(nickname);
        messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);

        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId()+"/start", "");
        }
    }

    @MessageMapping("/multi/bottom")
    public void bottomMove(Principal principal) {
        String nickname = principal.getName();
        GameRoomRedis room = multiService.bottomMove(nickname);
        messageTemplate.convertAndSend("/sub/room/"+room.getId(), room);

        if(room.getStartTime() == null) {
            messageTemplate.convertAndSend("/sub/room/"+room.getId()+"/start", "");
        }
    }
}
