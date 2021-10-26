package dev.gunlog.api.member.service;

import dev.gunlog.api.member.domain.MemberRepository;
import dev.gunlog.api.member.dto.PasswordMatchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public PasswordMatchResponseDto checkMember(String memberId) {
        return new PasswordMatchResponseDto(memberRepository.findByMemberId(memberId).isPresent());
    }
}