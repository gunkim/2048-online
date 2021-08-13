package dev.gunlog.member.domain;

import dev.gunlog.SpringBootTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTests extends SpringBootTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    public MemberRepositoryTests(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Test
    public void saveTest() {
        memberRepository.save(Member.builder()
                .username("gunkim")
                .password("test")
                .role(Role.USER)
                .userIp("0.0.0.0")
                .build());

        Member member = memberRepository.findAll().get(0);
        assertThat(member).isNotNull();
        assertThat(member.getUsername()).isEqualTo("gunkim");
        assertThat(member.getPassword()).isEqualTo("test");
        assertThat(member.getRole()).isEqualTo(Role.USER);
        assertThat(member.getUserIp()).isEqualTo("0.0.0.0");
    }
}