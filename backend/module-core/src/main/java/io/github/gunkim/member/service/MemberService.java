package io.github.gunkim.member.service;

import io.github.gunkim.member.domain.MemberRepository;
import io.github.gunkim.member.dto.PasswordMatchResponseDto;
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