<script lang="ts">
    import {goto} from "$app/navigation";
    import {onMount} from "svelte";
    import {stompClient} from "$lib/stomp.ts";
    import {leaveRoom, readyRoom, startRoom} from "$lib/apis/rooms.ts";
    import {Client} from "stompjs";
    import type {LoadResponse} from "./+page";
    import {Alert, Button, ButtonGroup, Card} from "flowbite-svelte";
    import {InfoCircleSolid} from 'flowbite-svelte-icons'
    import {writable} from "svelte/store";
    import {roomTimer} from "./store.ts";

    export let data: LoadResponse;

    let {id, title, players} = data;
    let alert: string;

    onMount(() => {
        const client: Client = stompClient();

        client.connect({}, () => {
            client.subscribe(`/topic/rooms/${id}/wait`, (response) => {
                const room = JSON.parse(response.body);

                title = room.title;
                players = room.players;
            });
            client.subscribe(`/topic/rooms/${id}/start`, (res) => {
                roomTimer.set(res.body);
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

    const readyCss = (ready: boolean) => {
        return ready ? 'border-b-emerald-500 border-b-4' : '';
    }
    const hostCss = (host: boolean) => {
        return host ? 'border-t-amber-500 border-t-4' : '';
    }
    const closeAlert = () => {
        alert = '';
    };
</script>

<svelte:head>
    <title>대기실</title>
    <meta name="description" content="2048 대기실"/>
</svelte:head>

{#if alert}
    <Alert dismissable on:close={closeAlert}>
        <InfoCircleSolid slot="icon" class="w-4 h-4" />
        <span class="font-medium">다시 확인해보세요!</span>
        {alert}
    </Alert>
{/if}

<div class="flex justify-center">
    <div style="text-align: center;" class="grid grid-cols-16">
        <ButtonGroup style="margin: 0 auto;">
            <Button outline color="red" on:click={startAction}>Start</Button>
            <Button outline color="green" on:click={readyAction}>Ready</Button>
            <Button outline color="yellow" on:click={leaveAction}>Exit</Button>
        </ButtonGroup>
        <Card padding="none" size="xl" class="grid md:grid-cols-2">
            {#each players as player(player.id)}
                <figure class={`${readyCss(player.ready)} ${hostCss(player.host)} flex flex-col justify-center items-center p-8 text-center bg-white rounded-t-lg border-b md:rounded-t-none md:rounded-tl-lg md:border-r`}>
                    <figcaption class="flex justify-center items-center space-x-3">
                        <img class="w-9 h-9 rounded-full"
                             src={player.profileImageUrl}
                             alt={player.name}/>
                        <div class="space-y-0.5 font-medium dark:text-white text-left">
                            <div>{player.name}</div>
                        </div>
                    </figcaption>
                </figure>
            {/each}
        </Card>
    </div>
</div>
