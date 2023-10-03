<script lang="ts">
    import type {GamerProfile} from "$lib/types";

    export let gamers: GamerProfile[] = [];
    export let timer: number;
</script>

<div id="resultModal" class="modal">
    <div class="modal-content">
        <h2>게임 결과</h2>
        <ul id="result">
            {#each gamers as gamer(gamer.id)}
                <li>
                    <p class="player-avatar" style="background-image: url({gamer.profileImageUrl});"></p>
                    <p class="player-info">{gamer.name} : {gamer.score}</p>
                </li>
            {/each}
        </ul>
        <span id="redirect-timer">{timer}</span>
    </div>
</div>

<style>
    .modal {
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
    }

    .modal-content {
        background-color: white;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 50%;
        box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.2);
        text-align: center;
    }


    #result {
        list-style: none;
        padding: 0;
    }

    .player-avatar {
        display: inline-block;
        vertical-align: middle;
        width: 30px;
        height: 30px;
        background-size: cover;
        border-radius: 50%;
        margin-right: 10px;
    }

    .player-info {
        display: inline-block;
        vertical-align: middle;
        font-size: 16px;
    }

    #redirect-timer {
        font-weight: bold;
    }
</style>
