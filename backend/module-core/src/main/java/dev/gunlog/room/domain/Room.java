package dev.gunlog.room.domain;

import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import dev.gunlog.member.domain.Member;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long id;

    @NotBlank
    @Column(name = "ROOM_TITLE")
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_PERSONNEL")
    private Personnel personnel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_MODE")
    private Mode mode;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_NO")
    private Member member;

    @Builder
    public Room(Long id, String title, Personnel personnel, Mode mode, Member member) {
        this.id = id;
        this.title = title;
        this.personnel = personnel;
        this.mode = mode;
        this.member = member;
    }
}