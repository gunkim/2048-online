<script lang="ts">
    import {onMount} from 'svelte';
    import Rooms from "./Rooms.svelte";
    import Alert from "./Alert.svelte";
    import {createRoom, getRooms, joinRoom} from "$lib/apis/rooms.ts";
    import {goto} from "$app/navigation";
    import {stompClient} from "$lib/stomp.ts";
    import type {Client} from "stompjs";
    import type {Room} from "$lib/types.ts";

    let rooms: Room[] = [];
    let title: string = '';
    let alert: string = '';

    onMount(() => {
        const client: Client = stompClient();

        client.connect({}, () => {
            client.subscribe('/topic/rooms', (response) => {
                rooms = JSON.parse(response.body);
            });
        });
    });

    const createRoomAction = async () => {
        const response = await createRoom(title);

        if (!response.ok) {
            const json = await response.json();
            alert = json.message;
        }

        const roomId = await response.json();
        await goto(`/room/${roomId}`);
    };

    const joinRoomAction = async (roomId: string) => {
        const response = await joinRoom(roomId);
        if (response.status !== 200) {
            alert = '방에 입장할 수 없습니다.';
            return;
        }
        await goto(`/room/${roomId}`);
    }

    onMount(async () => {
        rooms = await getRooms();
    })
</script>

<svelte:head>
    <title>Home</title>
    <meta name="description" content="2048 Home"/>
</svelte:head>

<section>
    <Alert alert={alert}/>
    <input id="room-title" type="text" class="text-box" bind:value={title} placeholder="생성할 방 이름을 입력하세요.">

    <div class="btn-group">
        <button class="btn secondary-btn" on:click={() => location.href='/api/oauth2/authorization/google'}>로그인</button>
        <button class="btn primary-btn" on:click={createRoomAction}>방 만들기</button>
    </div>
    <Rooms rooms={rooms} joinRoomAction={joinRoomAction}/>
</section>
