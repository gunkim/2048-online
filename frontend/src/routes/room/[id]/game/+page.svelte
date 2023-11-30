<script lang="ts">
    import {onMount} from "svelte";
    import {stompClient} from "$lib/stomp.ts";
    import Board from "./Board.svelte";
    import {goto} from "$app/navigation";
    import type {Client} from "stompjs";
    import type {Gamer, GamerProfile} from "$lib/types";
    import type {LoadResponse} from "./+page";
    import {Modal, Progressbar} from "flowbite-svelte";
    import {BellOutline} from 'flowbite-svelte-icons';
    import {roomTimer} from "../store.ts";

    export let data: LoadResponse;
    let {id, endTime, gamers}: {
        id: string,
        endTime: Date,
        gamers: Gamer[]
    } = data;
    let client: Client;
    let isEnd: boolean = false;
    let timer: number = 0;
    let roomTime: number = 0;
    let redirectTimer: number = 5;

    let gameResults: GamerProfile[];

    onMount(() => {
        roomTimer.subscribe(value => {
            timer = value;
            roomTime = value;
        });
        client = stompClient();

        client.connect({}, () => {
            client.subscribe(`/topic/rooms/${id}/game`, (response) => {
                gamers = JSON.parse(response.body);
            });
            client.subscribe(`/topic/rooms/${id}/game-end`, (response) => {
                gameResults = JSON.parse(response.body);
                isEnd = true;

                const REDIRECT_TIMER = 5;

                let count = 0;
                setInterval(() => {
                    if (count > REDIRECT_TIMER) return;

                    redirectTimer = REDIRECT_TIMER - count;
                    count++;
                    if (count === 5) {
                        setTimeout(() => {
                            goto(`/room/${id}`);
                        }, 2_500);
                    }
                }, 1_000);
            });
        });
        countGameTimer();
    });

    const countGameTimer = () => {
        const end: Date = new Date(endTime);
        const timerId: NodeJS.Timeout = setInterval(() => {
            const now: Date = new Date();

            const diffTime: number = Math.abs(end.getTime() - now.getTime());
            timer = Math.floor(diffTime / 1000);

            if (now > end) {
                clearInterval(timerId);
            }
        }, 1_000);
    }


    const onKeyDown = (e: KeyboardEvent) => {
        if (isEnd) return;

        const arrows: { [key: string]: string } = {
            "ArrowLeft": "LEFT",
            "ArrowRight": "RIGHT",
            "ArrowUp": "TOP",
            "ArrowDown": "DOWN"
        };

        if (e.code in arrows) {
            const arrow = arrows[e.code];
            client.send(`/app/rooms/${id}/move`, {},
                JSON.stringify({direction: arrow}))
        }
    }
    let progress = 100;
    $: progress = timer / roomTime * 100;
</script>

<Progressbar progress={progress} size="h-4"/>

<div class="flex justify-center">
    <div style="width:820px;" >
        {#each gamers as gamer(gamer.userId)}
            <Board {gamer}/>
        {/each}
    </div></div>

<Modal bind:open={isEnd} size="md" autoclose={false} dismissable={false}>
    <div class="text-center">
        <BellOutline class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200"/>
        <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">게임 결과</h3>
        {#each gameResults as gamer}
            <div class="flex items-center space-x-4">
                <img class="w-9 h-9 rounded-full"
                     src={gamer.profileImageUrl}
                     alt={gamer.name}/>
                <div class="space-y-1 font-medium dark:text-white">
                    <div>{gamer.name}</div>
                    <div class="text-sm text-gray-500 dark:text-gray-400">{gamer.score}점</div>
                </div>
            </div>
        {/each}
    </div>
</Modal>

<svelte:window on:keydown|preventDefault={onKeyDown}/>
