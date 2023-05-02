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
            </div>`).join('');

const stompClient = createStompClient((stompClient) => {
    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/rooms/${roomId}/game`, (response) => {
            const gamers = JSON.parse(response.body);
            putHtml('boards', createGamers(gamers));
        });
        stompClient.subscribe(`/topic/rooms/${roomId}/game-end`, (response) => {
            const gamers = JSON.parse(response.body);
            console.log(gamers);

            console.log('게임 종료! 5초 뒤 대기실로 이동합니다.');
            let count = 0;
            setInterval(() => {
                console.log((5-count)+'초.');
                count++;
                if(count === 5) {
                    window.location.href = `/waitroom/${roomId}`
                }
            }, 1000);
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
