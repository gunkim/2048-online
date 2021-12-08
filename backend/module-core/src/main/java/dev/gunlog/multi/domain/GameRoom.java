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
    @NotNull
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
    public GameRoom(Long id, String title, List<Player> players, Mode gameMode, Personnel maxNumberOfPeople, boolean isStart) throws IllegalArgumentException {
        this.id = id;
        this.title = title;
        setPlayersAndHost(players);
        this.gameMode = gameMode;
        this.maxNumberOfPeople = maxNumberOfPeople;
        this.isStart = isStart;
    }
    private void setPlayersAndHost(List<Player> players) throws IllegalArgumentException {
        if(players == null || players.isEmpty()) {
            throw new IllegalArgumentException("플레이어 초기화 값이 잘못되었습니다.");
        }
        this.players = players;
        this.host = Optional.ofNullable(players.get(0).getNickname())
                .orElseThrow(() -> new IllegalArgumentException("호스트 네임이 잘못되었습니다."));
    }
    public GameRoom start() {
        this.isStart = true;
        this.startTime = LocalDateTime.now();
        this.players.stream().forEach(player -> player.setGameInfo(new Game()));
        return this;
    }
    public GameRoom stop() {
        this.isStart = false;
        this.startTime = null;
        this.players.stream().forEach(player -> player.setGameInfo(null));
        return this;
    }
    public GameRoom addPlayer(String nickname) {
        if(this.isStart) {
            return this;
        }
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