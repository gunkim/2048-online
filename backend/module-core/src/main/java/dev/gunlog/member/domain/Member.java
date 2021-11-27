package dev.gunlog.member.domain;

import com.sun.istack.NotNull;
import dev.gunlog.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Entity
@Getter
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NO")
    private Long id;
    @NotBlank
    @Column(name = "MEMBER_ID", updatable = false)
    private String memberId;
    @NotBlank
    @Column(name = "MEMBER_PWD")
    private String password;
    @NotBlank
    @Column(name = "MEMBER_NICKNAME", unique = true)
    private String nickname;
    @Column(name = "MEMBER_PICTURE")
    private String picture;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_ROLE")
    private Role role = Role.USER;
    @NotNull
    @Column(name = "MEMBER_REG_IP")
    private String regIp;

    @Builder
    public Member(Long id, String memberId, String password, String nickname, Role role, String regIp, String picture) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.nickname = nickname;
        setRole(role);
        this.regIp = regIp;
        this.picture = picture;
    }
    private void setRole(Role role) {
        this.role = Optional.ofNullable(role).orElse(Role.USER);
    }
}