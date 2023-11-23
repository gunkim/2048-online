<script lang="ts">
    import Grid from "./Grid.svelte";
    import type {Gamer} from "$lib/types";

    export let gamer: Gamer;
</script>

<div class="grid-container">
    {#each gamer.board as row}
        <div class="grid-row">
            {#each row as cell}
                <Grid {cell}/>
            {/each}
        </div>
    {/each}
</div>

<style>
    .grid-container {
        display: grid;
        float: left;
        margin: 5px;
        grid-template-columns: repeat(4, 1fr);
        grid-template-rows: repeat(4, 1fr);
        gap: 10px;
        width: 400px;
        height: 400px;
        background-color: #bbada0;
        border-radius: 10px;
        padding: 10px;
        box-sizing: border-box;
    }

    .grid-row {
        display: contents;
    }
</style>
