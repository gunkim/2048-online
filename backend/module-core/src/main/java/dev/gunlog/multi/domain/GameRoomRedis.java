package dev.gunlog.multi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gunlog.room.domain.enums.Mode;
import dev.gunlog.room.domain.enums.Personnel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Getter
@ToString
@RedisHash("room")
public class GameRoomRedis implements Serializable {
    @Id
    @Indexed
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String host;
    @Setter @NotNull
    @Indexed
    private List<PlayerRedis> players = new LinkedList<>();
    @NotNull
    private Mode gameMode;
    @NotNull
    private Personnel maxNumberOfPeople;
    @NotNull
    private boolean isStart;
    @Setter @JsonIgnore
    private LocalDateTime startTime;

    @Builder
    public GameRoomRedis(Long id, String title, List<PlayerRedis> players, Mode gameMode, Personnel maxNumberOfPeople, boolean isStart, String host) {
        this.id = id;
        this.title = title;
        setPlayers(players);
        this.gameMode = gameMode;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.isStart = isStart;
        setHost(host);
    }
    private void setHost(String host) {
        this.host = Optional.ofNullable(host).orElseThrow(IllegalArgumentException::new);
    }
    private void setPlayers(List<PlayerRedis> players) {
        this.players = Optional.ofNullable(players).orElse(this.players);
    }
    public GameRoomRedis gameStart() {
        this.isStart = true;
        this.startTime = LocalDateTime.now();
        this.players.stream().forEach(player -> player.setGameInfo(new GameRedis()));
        return this;
    }
    public void gameStop() {
        this.isStart = false;
        this.startTime = null;
        this.players.stream().forEach(player -> player.setGameInfo(null));
    }
    public void addPlayer(String nickname) {
        boolean isNotPlayerFull = this.maxNumberOfPeople.getSize() > players.size();
        if(isNotPlayerFull) {
            this.players.add(new PlayerRedis(nickname));
        }
    }
    public void exitPlayer(String nickname) {
        this.players = this.players.stream()
                .filter(player -> !nickname.equals(player.getNickname()))
                .collect(toList());
    }
}