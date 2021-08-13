package dev.gunlog.room.domain;

import dev.gunlog.member.domain.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Personnel personnel;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
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