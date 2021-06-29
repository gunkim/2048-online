package dev.gunlog;

import dev.gunlog.member.domain.Member;
import dev.gunlog.member.domain.MemberRepository;
import dev.gunlog.member.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("========= Back-End Server Start =========");
    }
    @Bean
    public CommandLineRunner runner(MemberRepository memberRepository, PasswordEncoder passwordEncoder) throws SQLException {
        return (args -> {
            memberRepository.save(Member.builder()
                    .username("gunkim")
                    .password(passwordEncoder.encode("test"))
                    .role(Role.USER)
                    .userIp("127.0.0.1")
                    .build()
            );
        });
    }
}