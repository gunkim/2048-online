<script lang="ts">
    import {Table, TableBody, TableBodyCell, TableBodyRow, TableHead, TableHeadCell} from 'flowbite-svelte';
    import {getRooms, joinRoom} from "$lib/apis/rooms.ts";
    import {goto} from "$app/navigation";
    import {stompClient} from "$lib/stomp.ts";
    import type {Client} from "stompjs";
    import {onMount} from 'svelte';

    let rooms = [];
    let alert: string = '';

    onMount(async () => {
        const client: Client = stompClient();

        client.connect({}, () => {
            client.subscribe('/topic/rooms', (response) => {
                rooms = JSON.parse(response.body);
            });
        });

        rooms = await getRooms();
    });

    const joinRoomAction = async (roomId: string) => {
        const response = await joinRoom(roomId);
        if (response.status !== 200) {
            alert = '방에 입장할 수 없습니다.';
            return;
        }
        await goto(`/room/${roomId}`);
    }
</script>

<Table class="text-center" hoverable={true}>
    <caption class="p-5 text-lg font-semibold text-left text-gray-900 bg-white dark:text-white dark:bg-gray-800">
        게임에 참여해보세요!
        <p class="mt-1 text-sm font-normal text-gray-500 dark:text-gray-400">게임이 없다면 직접 만들어보세요.</p>
    </caption>
    <TableHead>
        <TableHeadCell>번호</TableHeadCell>
        <TableHeadCell>제목</TableHeadCell>
        <TableHeadCell>인원수</TableHeadCell>
        <TableHeadCell>
            <span class="sr-only">입장</span>
        </TableHeadCell>
    </TableHead>
    <TableBody class="divide-y">
        {#if rooms.length === 0}
            <TableBodyRow>
                <TableBodyCell colspan="4">아직 게임이 없어요!</TableBodyCell>
            </TableBodyRow>
        {/if}
        {#each rooms as room (room.id)}
            <TableBodyRow>
                <TableBodyCell>{room.id}</TableBodyCell>
                <TableBodyCell>{room.title}</TableBodyCell>
                <TableBodyCell>{room.currentPlayer}/{room.maxPlayer}</TableBodyCell>
                <TableBodyCell >
                    <a href="#" on:click={joinRoomAction(room.id)} class="font-medium text-primary-600 hover:underline dark:text-primary-500">입장</a>
                </TableBodyCell>
            </TableBodyRow>
        {/each}
    </TableBody>
</Table>