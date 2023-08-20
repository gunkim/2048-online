const REDIRECT_TIME = 5;

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

const createResult = (gamers) => gamers.map(gamer =>
    `<li>
    <p class="player-avatar" style="background-image: url(${gamer.profileImageUrl});"></p>
    <p class="player-info">${gamer.name} : ${gamer.score}</p>
  </li>`
).join('');

const stompClient = createStompClient((stompClient) => {
    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/rooms/${roomId}/game`, (response) => {
            const gamers = JSON.parse(response.body);
            putHtml('boards', createGamers(gamers));
        });
        stompClient.subscribe(`/topic/rooms/${roomId}/game-end`, (response) => {
            const gamers = JSON.parse(response.body);

            putHtml('result', createResult(gamers));

            const resultModal = document.getElementById('resultModal');
            resultModal.style.display = 'block';
            putHtml('redirect-timer', `${REDIRECT_TIME}초`);

            let count = 0;
            setInterval(() => {
                const time = REDIRECT_TIME - count;
                putHtml('redirect-timer', `${time}초`);
                count++;
                if (count === 5) {
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
