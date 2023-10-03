<script lang="ts">
    export let cell: string;
</script>

<div class="grid-cell" data-value="{cell}"></div>

<style>
    .grid-cell {
        background-color: #cdc1b4;
        border-radius: 5px;
        width: 100%;
        height: 0;
        padding-top: 100%;
        position: relative;
    }

    .grid-cell::before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 2em;
        font-weight: bold;
        color: #776e65;
    }

    .grid-cell[data-value='ONE']::before {
        content: '2';
        background-color: #eee4da;
    }

    .grid-cell[data-value='TWO']::before {
        content: '4';
        background-color: #ede0c8;
    }

    .grid-cell[data-value='THREE']::before {
        content: '8';
        background-color: #f2b179;
    }

    .grid-cell[data-value='FOUR']::before {
        content: '16';
        background-color: #f59563;
    }

    .grid-cell[data-value='FIVE']::before {
        content: '32';
        background-color: #f67c5f;
    }

    .grid-cell[data-value='SIX']::before {
        content: '64';
        background-color: #f65e3b;
    }

    .grid-cell[data-value='SEVEN']::before {
        content: '128';
        background-color: #edcf72;
    }

    .grid-cell[data-value='EIGHT']::before {
        content: '256';
        background-color: #edcc61;
    }

    .grid-cell[data-value='NINE']::before {
        content: '512';
        background-color: #edc850;
    }

    .grid-cell[data-value='TEN']::before {
        content: '1024';
        background-color: #edc53f;
    }

    .grid-cell[data-value='ELEVEN']::before {
        content: '2048';
        background-color: #edc22e;
    }

    .grid-cell[data-value='THREE']::before,
    .grid-cell[data-value='SIX']::before,
    .grid-cell[data-value='NINE']::before,
    .grid-cell[data-value='ELEVEN']::before {
        color: #f9f6f2;
    }

    .grid-cell[data-value='SEVEN']::before,
    .grid-cell[data-value='EIGHT']::before,
    .grid-cell[data-value='NINE']::before {
        font-size: 1.6em;
    }

    .grid-cell[data-value='TEN']::before,
    .grid-cell[data-value='ELEVEN']::before {
        font-size: 1.4em;
    }
</style>
