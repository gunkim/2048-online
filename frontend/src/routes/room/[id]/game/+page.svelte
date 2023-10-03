<script>
    import {onMount} from "svelte";
    import {stompClient} from "$lib/stomp.ts";
    import Board from "./Board.svelte";
    import {goto} from "$app/navigation";
    import Modal from "./Modal.svelte";

    export let data;
    let {id, endTime, gamers} = data;
    let client;
    let isEnd = false;
    let timer = 30;
    let redirectTimer = 5;

    let gameResults;

    onMount(() => {
        client = stompClient();

        client.connect({}, () => {
            client.subscribe(`/topic/rooms/${id}/game`, (response) => {
                gamers = JSON.parse(response.body);
            });
            client.subscribe(`/topic/rooms/${id}/game-end`, (response) => {
                gameResults = JSON.parse(response.body);

                const REDIRECT_TIMER = 5;

                let count = 0;
                setInterval(() => {
                    if(count > REDIRECT_TIMER) return;

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

        const end = new Date(endTime);
        const timerId = setInterval(() => {
            const now = new Date();

            const diffTime = Math.abs(end - now);
            const diffSeconds = Math.floor(diffTime / 1000);

            timer = diffSeconds;

            if (now > end) {
                clearInterval(timerId);
                isEnd = true;
            }
        }, 1_000);
    });

    const arrows = {
        "ArrowLeft": (client) => client.send(`/app/rooms/${id}/move`, {},
            JSON.stringify({direction: 'LEFT'})),
        "ArrowRight": (client) => client.send(`/app/rooms/${id}/move`, {},
            JSON.stringify({direction: 'RIGHT'})),
        "ArrowUp": (client) => client.send(`/app/rooms/${id}/move`, {},
            JSON.stringify({direction: 'TOP'})),
        "ArrowDown": (client) => client.send(`/app/rooms/${id}/move`, {},
            JSON.stringify({direction: 'DOWN'}))
    };

    const onKeyDown =(e) => {
        if(isEnd) return;

        if (e.code in arrows) {
            arrows[e.code](client);
        }
    }
</script>

<section>
    <div class="timer-container">
        <span id="timer">
            {#if isEnd}
                <span style="color: #f75d5d;">게임 종료!</span>
            {:else}
                {timer}
            {/if}
        </span>
    </div>
    <div class="boards-container" id="boards">
        {#each gamers as gamer(gamer.id)}
            <Board {gamer}/>
        {/each}
    </div>
</section>

{#if gameResults}
    <Modal gamers={gameResults} timer={redirectTimer}/>
{/if}
<svelte:window on:keydown|preventDefault={onKeyDown}/>

<style>
    .timer-container {
        margin-top: 20px;
        margin-bottom: 20px;
        font-size: 24px;
        font-weight: bold;
        color: #776e65;
        padding: 20px;
        border-radius: 5px;
    }


    #timer {
        background-color: #eee4da;
        color: #776e65;
        padding: 10px 20px;
        border-radius: 5px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }


    .boards-container {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-template-rows: repeat(2, 1fr);
        gap: 20px;
    }

    #redirect-timer ul {
        padding: 0;
    }
</style>
