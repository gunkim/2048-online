//package dev.gunlog.multi.domain;
//
//import dev.gunlog.multi.domain.enums.Mode;
//import dev.gunlog.multi.domain.enums.Personnel;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//@ActiveProfiles("dev")
//@SpringBootTest
//public class GameRoomRepositoryTests {
//    @Autowired
//    private GameRoomRepository repository;
//    @Test
//    public void test() {
//        repository.deleteAll();
//        Player player = new Player("gunkim");
//        player.setGameInfo(new Game());
//        repository.save(GameRoom.builder()
//                .title("방 이름")
//                .players(List.of(player))
//                .host("gunkim")
//                .isStart(false)
//                .maxNumberOfPeople(Personnel.FOUR)
//                .gameMode(Mode.SPEED_ATTACK)
//                .build());
//        System.out.println("===========================");
//        repository.findAll().forEach(System.out::println);
//    }
//}