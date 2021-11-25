package dev.gunlog.member.service;

import dev.gunlog.SpringBootTestSupport;
import dev.gunlog.member.domain.Member;
import dev.gunlog.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Transactional
public class MemberServiceTests extends SpringBootTestSupport {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    public MemberServiceTests(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Test
    @DisplayName("회원 가입 여부 체크 테스트")
    public void checkUserTest() {
        final String memberId = "gunkim";
        given(memberRepository.findByMemberId(memberId)).willReturn(Optional.of(Member.builder().build()));

        final boolean isUser = memberService.checkMember(memberId).isMatchPassword();

        assertThat(isUser).isTrue();
    }
}