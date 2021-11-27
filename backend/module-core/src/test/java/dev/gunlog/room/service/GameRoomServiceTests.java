//package dev.gunlog.room.service;
//
//import dev.gunlog.SpringBootTestSupport;
//import dev.gunlog.room.domain.enums.Mode;
//import dev.gunlog.room.domain.enums.Personnel;
//import dev.gunlog.member.domain.Member;
//import dev.gunlog.member.domain.MemberRepository;
//import dev.gunlog.room.domain.Room;
//import dev.gunlog.room.domain.RoomRepository;
//import dev.gunlog.room.dto.RoomCreateRequestDto;
//import dev.gunlog.room.dto.RoomListResponseDto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManagerFactory;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@Transactional
//public class GameRoomServiceTests extends SpringBootTestSupport {
//    @InjectMocks
//    private RoomService roomService;
//    @Mock
//    private RoomRepository roomRepository;
//    @Mock
//    private MemberRepository memberRepository;
//
//    public GameRoomServiceTests(EntityManagerFactory entityManagerFactory) {
//        super(entityManagerFactory);
//    }
//
//    @Test
//    @DisplayName("방 만들기 테스트")
//    public void createRoomTest() {
//        final String memberId = "gunkim";
//        //given
//        RoomCreateRequestDto requestDto = RoomCreateRequestDto.builder()
//                .title("새로운 방입니다.")
//                .mode(Mode.SPEED_ATTACK)
//                .personnel(Personnel.FOUR)
//                .build();
//        given(roomRepository.save(any())).willReturn(Room.builder().id(1L).build());
//        given(memberRepository.findByMemberId(memberId)).willReturn(Optional.of(Member.builder().build()));
//
//        //when
//        Long id = roomService.createRoom(requestDto, memberId);
//
//        //then
//        assertThat(id).isNotNull();
//        assertThat(id).isEqualTo(1L);
//    }
//    @Test
//    @DisplayName("모든 방 조회 테스트")
//    public void getAllRoomsTest() {
//        //given
//        given(roomRepository.findAll()).willReturn(List.of(
//                Room.builder()
//                .id(1L)
//                .title("테스트 방1")
//                .mode(Mode.SPEED_ATTACK)
//                .personnel(Personnel.FOUR)
//                .member(Member.builder().nickname("test-user1").build())
//                .build()
//        ));
//
//        //when
//        List<RoomListResponseDto> rooms = roomService.getAllRooms();
//
//        //then
//        assertThat(rooms.get(0)).isNotNull();
//        assertThat(rooms.get(0).getTitle()).isEqualTo("테스트 방1");
//        assertThat(rooms.get(0).getUsername()).isEqualTo("test-user1");
//        assertThat(rooms.get(0).getMode()).isEqualTo(Mode.SPEED_ATTACK);
//        assertThat(rooms.get(0).getPersonnel()).isEqualTo(Personnel.FOUR);
//    }
//}