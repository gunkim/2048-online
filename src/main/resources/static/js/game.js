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
