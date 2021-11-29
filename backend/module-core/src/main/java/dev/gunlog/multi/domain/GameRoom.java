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
public class GameRoom implements Serializable {
    @Id
    @Indexed
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String host;
    @Setter @NotNull
    @Indexed
    private List<Player> players = new LinkedList<>();
    @NotNull
    private Mode gameMode;
    @NotNull
    private Personnel maxNumberOfPeople;
    @NotNull
    private boolean isStart;
    @Setter @JsonIgnore
    private LocalDateTime startTime;

    @Builder
    public GameRoom(Long id, String title, List<Player> players, Mode gameMode, Personnel maxNumberOfPeople, boolean isStart, String host) {
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
    private void setPlayers(List<Player> players) {
        this.players = Optional.ofNullable(players).orElse(this.players);
    }
    public GameRoom gameStart() {
        this.isStart = true;
        this.startTime = LocalDateTime.now();
        this.players.stream().forEach(player -> player.setGameInfo(new Game()));
        return this;
    }
    public GameRoom gameStop() {
        this.isStart = false;
        this.startTime = null;
        this.players.stream().forEach(player -> player.setGameInfo(null));
        return this;
    }
    public GameRoom addPlayer(String nickname) {
        boolean isNotPlayerFull = this.maxNumberOfPeople.getSize() > players.size();
        if(isNotPlayerFull) {
            this.players.add(new Player(nickname));
        }
        return this;
    }
    public GameRoom exitPlayer(String nickname) {
        this.players = this.players.stream()
                .filter(player -> !nickname.equals(player.getNickname()))
                .collect(toList());
        return this;
    }
}