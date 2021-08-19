package dev.gunlog.member.domain;

import com.sun.istack.NotNull;
import dev.gunlog.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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
    @Column(name = "MEMBER_NAME")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_ROLE")
    private Role role;

    @NotNull
    @Column(name = "MEMBER_REG_IP")
    private String regIp;

    @Builder
    public Member(Long id, String memberId, String password, String name, Role role, String regIp) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.role = role;
        this.regIp = regIp;
    }
}