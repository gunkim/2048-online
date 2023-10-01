<script>
    export let data;

    let players = data.players;
</script>

<section>
    <div class="room-title" id="room-title"></div>
    <ul class="player-list" id="player-list">
        {#each players as player(player.id)}
            <li class="player">
                <div class="player-info">
                    <div class="player-avatar" style="background-image: url({player.profileImageUrl})"></div>
                    <div>
                        {#if player.host}
                            &#9813;
                        {/if}
                        {player.name}
                    </div>
                </div>
                {#if player.ready}
                    <div class="status ready">레디</div>
                {/if}
                <div class="kick" data-gamer-id="{player.id}">강퇴</div>
            </li>
        {/each}
    </ul>
    <button class="btn primary-btn full">시작하기</button>
    <div class="btn-group">
        <button class="btn third-btn">준비 | 준비 취소</button>
        <button class="btn secondary-btn">방 나가기</button>
    </div>
</section>

<style>
    .player-list {
        list-style: none;
        padding: 0;
    }

    .player {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10px;
        border-bottom: 1px solid #9b9b9b;
    }

    .player:last-child {
        border-bottom: none;
    }

    .player-info {
        display: flex;
        align-items: center;
    }

    .status {
        font-size: 14px;
        color: #888;
    }

    .ready {
        color: #1e90ff;
    }

    .kick {
        color: #ff4500;
        cursor: pointer;
    }

    .room-title {
        font-size: 24px;
        font-weight: bold;
        color: #776E65;
        text-align: center;
        margin-bottom: 20px;
    }
</style>