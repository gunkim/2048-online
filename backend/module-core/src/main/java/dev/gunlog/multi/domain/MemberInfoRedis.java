package dev.gunlog.multi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@ToString
@RedisHash("memberInfo")
public class MemberInfoRedis {
    @Id
    private String nickname;
    private Long roomId;

    @Builder
    public MemberInfoRedis(String nickname, Long roomId) {
        this.nickname = nickname;
        this.roomId = roomId;
    }
}
