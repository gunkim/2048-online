const createGamers = (gamers) => gamers.map(gamer => `
                <div class="board" data-value="${gamer.userId}">
                <div class="score-container">
                    <div class="player-name">${gamer.name}</div>
                    <div class="score">${gamer.score}</div>
                </div>
                <div class="grid-container">
                    ${gamer.board.map(row => `<div class="grid-row">
                                ${row.map(cell => `<div class="grid-cell" data-value="${cell}"></div>`).join('')}
                            </div>`).join('')}
                </div>
            </div>`);

const stompClient = createStompClient((stompClient) => {
    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/rooms/${roomId}/game`, (response) => {
            const gamers = JSON.parse(response.body);
            putHtml('boards', createGamers(gamers));
        });
    });
});

const arrows = {
    "ArrowLeft": (stompClient) => stompClient.send(`/app/rooms/${roomId}/move`, {},
        JSON.stringify({direction: 'LEFT'})),
    "ArrowRight": (stompClient) => stompClient.send(`/app/rooms/${roomId}/move`, {},
        JSON.stringify({direction: 'RIGHT'})),
    "ArrowUp": (stompClient) => stompClient.send(`/app/rooms/${roomId}/move`, {},
        JSON.stringify({direction: 'TOP'})),
    "ArrowDown": (stompClient) => stompClient.send(`/app/rooms/${roomId}/move`, {},
        JSON.stringify({direction: 'DOWN'}))
};
