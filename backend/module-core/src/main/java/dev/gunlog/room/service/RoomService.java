package dev.gunlog.room.service;

import dev.gunlog.member.domain.Member;
import dev.gunlog.member.domain.MemberRepository;
import dev.gunlog.room.domain.Room;
import dev.gunlog.room.domain.RoomRepository;
import dev.gunlog.room.dto.RoomCreateRequestDto;
import dev.gunlog.room.dto.RoomListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public Long createRoom(RoomCreateRequestDto requestDto, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다. username : "+username));

        Room room = roomRepository.save(requestDto.toEntity(member));
        return room.getId();
    }
    public List<RoomListResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomListResponseDto::new)
                .collect(toList());
    }
}