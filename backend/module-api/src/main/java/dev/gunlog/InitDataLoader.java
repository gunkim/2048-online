package dev.gunlog;

import dev.gunlog.api.member.domain.Member;
import dev.gunlog.api.member.domain.MemberRepository;
import dev.gunlog.api.member.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("dev")
public class InitDataLoader {
    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            log.info("==== 테스트 데이터 입력");
            memberRepository.save(Member.builder()
                    .memberId("gunkim")
                    .password(passwordEncoder.encode("test"))
                    .role(Role.USER)
                    .name("김건")
                    .regIp("0.0.0.0")
                    .build());
            memberRepository.save(Member.builder()
                    .memberId("test")
                    .password(passwordEncoder.encode("test"))
                    .role(Role.USER)
                    .name("김건")
                    .regIp("0.0.0.0")
                    .build());
        };
    }
}
