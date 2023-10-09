<script lang="ts">
    import type {Room} from "$lib/apis/rooms.ts";

    export let rooms: Room[];
    export let joinRoomAction: (roomId: string) => void;
</script>

<div id="room-list" class="room-list">
    {#each rooms as room (room.id)}
        <div class="room-card" on:click={joinRoomAction(room.id)}>
            <div class="room-name">{room.title}</div>
            <span class="badge">{room.currentPlayer}/{room.maxPlayer}</span>
        </div>
    {/each}
</div>

<style>
    .badge {
        display: inline-block;
        padding: 4px 8px;
        font-size: 14px;
        font-weight: bold;
        color: #FFF;
        background-color: #BBADA0;
        border-radius: 15px;
        margin-right: 10px;
        position: absolute;
        top: -10px;
        left: -10px;
    }

    .room-list {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        grid-gap: 20px;
        margin-top: 20px;
    }

    .room-card {
        background-color: #EEE4DA;
        border: 5px solid #BBADA0;
        border-radius: 5px;
        padding: 20px;
        position: relative;
        cursor: pointer;
    }

    .room-card:hover {
        background-color: #BBADA0;
    }
</style>
