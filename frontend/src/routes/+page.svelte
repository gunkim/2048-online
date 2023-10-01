<script>
    import {onMount} from 'svelte';
    import Rooms from "./Rooms.svelte";
    import Alert from "./Alert.svelte";
    import {createRoom, getRooms, joinRoom} from "../apis/rooms.ts";
    import {goto} from "$app/navigation";

    let rooms = [];
    let title = '';
    let alert = '';

    const createRoomAction = async () => {
        const response = await createRoom(title);

        if (!response.ok) {
            const json = await response.json();
            alert = json.message;
        }

        const roomId = await response.json();
        await goto(`/room/${roomId}`);
    };

    const joinRoomAction = async (roomId) => {
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
