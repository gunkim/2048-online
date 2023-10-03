<script lang="ts">
    import Grid from "./Grid.svelte";
    import type {Gamer} from "$lib/types";

    export let gamer: Gamer;
</script>
<div class="board" data-value="{gamer.userId}">
    <div class="score-container">
        <div class="player-name">{gamer.name}</div>
        <div class="score">{gamer.score}</div>
    </div>
    <div class="grid-container">
        {#each gamer.board as row}
            <div class="grid-row">
                {#each row as cell}
                    <Grid {cell}/>
                {/each}
            </div>
        {/each}
    </div>
</div>

<style>
    .board {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .score {
        font-size: 28px;
        font-weight: bold;
        color: #776e65;
        align-self: center;
    }

    .player-name {
        font-size: 20px;
        color: #776e65;
        align-self: center;
        margin-right: 20px;
    }

    .grid-container {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        grid-template-rows: repeat(4, 1fr);
        gap: 10px;
        width: 400px;
        height: 400px;
        margin: 0 auto;
        background-color: #bbada0;
        border-radius: 10px;
        padding: 10px;
        box-sizing: border-box;
    }

    .grid-row {
        display: contents;
    }
</style>
