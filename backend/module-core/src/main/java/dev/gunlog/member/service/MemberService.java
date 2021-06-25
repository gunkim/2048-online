package dev.gunlog.member.service;

import dev.gunlog.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean checkUser(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }
}