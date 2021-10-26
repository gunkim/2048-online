package dev.gunlog.api.room.service;

import dev.gunlog.api.member.domain.Member;
import dev.gunlog.api.member.domain.MemberRepository;
import dev.gunlog.api.room.domain.Room;
import dev.gunlog.api.room.domain.RoomRepository;
import dev.gunlog.api.room.dto.RoomCreateRequestDto;
import dev.gunlog.api.room.dto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public Long createRoom(RoomCreateRequestDto requestDto, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. username : "+memberId));

        Room room = roomRepository.save(requestDto.toEntity(member));
        return room.getId();
    }
    public List<RoomListResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomListResponseDto::new)
                .collect(toList());
    }
}