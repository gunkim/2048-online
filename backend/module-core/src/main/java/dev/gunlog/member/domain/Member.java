package dev.gunlog.member.domain;

import com.sun.istack.NotNull;
import dev.gunlog.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull
    private String userIp;

    @Builder
    public Member(@NotNull Long id, @NotNull String username, @NotNull String password, @NotNull Role role, @NotNull String userIp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userIp = userIp;
    }
}