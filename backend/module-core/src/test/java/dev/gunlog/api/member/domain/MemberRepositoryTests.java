package dev.gunlog.api.member.domain;

import dev.gunlog.SpringBootTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class MemberRepositoryTests extends SpringBootTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    public MemberRepositoryTests(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Test
    public void saveTest() {
        memberRepository.save(Member.builder()
                .memberId("gunkim")
                .name("TEST USER")
                .password("test")
                .role(Role.USER)
                .regIp("0.0.0.0")
                .build());

        Member member = memberRepository.findAll().get(0);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("TEST USER");
        assertThat(member.getMemberId()).isEqualTo("gunkim");
        assertThat(member.getPassword()).isEqualTo("test");
        assertThat(member.getRole()).isEqualTo(Role.USER);
        assertThat(member.getRegIp()).isEqualTo("0.0.0.0");
    }
}