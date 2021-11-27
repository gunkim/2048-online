package dev.gunlog;

import dev.gunlog.member.domain.Member;
import dev.gunlog.member.domain.MemberRepository;
import dev.gunlog.member.domain.Role;
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
                    .nickname("김건1")
                    .regIp("0.0.0.0")
                    .build());
            memberRepository.save(Member.builder()
                    .memberId("test")
                    .password(passwordEncoder.encode("test"))
                    .role(Role.USER)
                    .nickname("김건2")
                    .regIp("0.0.0.0")
                    .build());
        };
    }
}
