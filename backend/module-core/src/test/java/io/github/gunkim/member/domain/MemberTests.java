package io.github.gunkim.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member POJO 테스트")
public class MemberTests {
    @Test
    @DisplayName("엔티티 생성 테스트")
    public void createTest() {
        Member member = Member.builder()
                .id(1l)
                .memberId("gunkim1234")
                .password("password")
                .nickname("건빵")
                .regIp("0.0.0.0")
                .role(Role.USER)
                .picture("image url")
                .build();
        assertThat(member.getMemberId()).isEqualTo("gunkim1234");
        assertThat(member.getPassword()).isEqualTo("password");
        assertThat(member.getNickname()).isEqualTo("건빵");
        assertThat(member.getRegIp()).isEqualTo("0.0.0.0");
        assertThat(member.getRole()).isEqualTo(Role.USER);
        assertThat(member.getPicture()).isEqualTo("image url");
    }
    @Test
    @DisplayName("엔티티 생성 시 기본값 테스트")
    public void createDefaultValueTest() {
        Member member = Member.builder().build();
        assertThat(member.getRole()).isEqualTo(Role.USER);
    }
}
