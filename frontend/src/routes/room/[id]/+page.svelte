<script>
    import Players from "./Players.svelte";
    import Title from "./Title.svelte";
    import {goto} from "$app/navigation";
    import Alert from "../../Alert.svelte";
    import {onMount} from "svelte";
    import {stompClient} from "$lib/stomp.ts";
    import {leaveRoom, readyRoom, startRoom} from "$lib/apis/rooms.ts";

    export let data;

    let {id, title, players} = data;
    let alert;

    onMount(() => {
        const client = stompClient();

        client.connect({}, () => {
            client.subscribe(`/topic/rooms/${id}/wait`, (response) => {
                const room = JSON.parse(response.body);

                title = room.title;
                players = room.players;
            });
            client.subscribe(`/topic/rooms/${id}/start`, () => {
                goto(`/room/${id}/game`);
            });
        });
    });

    const readyAction = async () => {
        await readyRoom(id);
    }
    const leaveAction = async () => {
        const response = await leaveRoom(id);

        if (response.status === 200) {
            await goto('/');
        }
    }
    const startAction = async () => {
        const response = await startRoom(id);

        if (!response.ok) {
            const json = await response.json();
            alert = json.message;
        }
    }
</script>

<svelte:head>
    <title>대기실</title>
    <meta name="description" content="2048 대기실"/>
</svelte:head>

<section>
    <Alert alert={alert}/>
    <Title {title}/>
    <Players {players}/>
    <button class="btn primary-btn full" on:click={startAction}>시작하기</button>
    <div class="btn-group">
        <button class="btn third-btn" on:click={readyAction}>준비 | 준비 취소</button>
        <button class="btn secondary-btn" on:click={leaveAction}>방 나가기</button>
    </div>
</section>
