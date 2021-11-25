package dev.gunlog.room.domain;

import dev.gunlog.SpringBootTestSupport;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import dev.gunlog.member.domain.Member;
import dev.gunlog.member.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class GameRoomRepositoryTests extends SpringBootTestSupport {
    @Autowired
    private RoomRepository roomRepository;

    public GameRoomRepositoryTests(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Test
    @DisplayName("Room Save 테스트")
    public void saveTest() {
        roomRepository.save(Room.builder()
                .title("테스트 방")
                .mode(Mode.SPEED_ATTACK)
                .personnel(Personnel.FOUR)
                .member(Member.builder()
                        .memberId("gunkim")
                        .nickname("TEST USER")
                        .password("gunkim")
                        .role(Role.USER)
                        .regIp("0.0.0.0")
                        .build())
                .build());

        Room room = roomRepository.findAll().get(0);
        assertThat(room).isNotNull();
        assertThat(room.getTitle()).isEqualTo("테스트 방");
        assertThat(room.getMode()).isEqualTo(Mode.SPEED_ATTACK);
        assertThat(room.getPersonnel()).isEqualTo(Personnel.FOUR);
        assertThat(room.getMember()).isNotNull();
        assertThat(room.getMember().getMemberId()).isEqualTo("gunkim");
        assertThat(room.getMember().getNickname()).isEqualTo("TEST USER");
    }
}