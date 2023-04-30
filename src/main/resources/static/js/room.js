const createRow = (row) => row.content.map(cell => `
<td class="${cell} cell">
    ${(2 ** levels[cell]) === 1 ? '' : 2 ** levels[cell]}
</td>
`).join('');

const createRows = (gamer) => gamer.board.rows.content.map(row => `
<tr>
    ${(createRow(row))}
</tr>
`).join('');

const createBoard = (room) => room.gamers.map(gamer => `
<td>
    <table>
        ${(createRows(gamer))}
    </table>
</td>
`).join('');

const createRoom = (room) => `
<h2>번호 : ${room.id}</h2>
<h2>방제 : ${room.title}</h2>
<h2>시작 여부 : ${room.start}</h2>
    
<table>
    <tbody>
        <tr>
            ${(createBoard(room))}
        </tr>
    </tbody>
</table>
`;

const levels = {
  "ZERO": 0,
  "ONE": 1,
  "TWO": 2,
  "THREE": 3,
  "FOUR": 4,
  "FIVE": 5,
  "SIX": 6,
  "SEVEN": 7,
  "EIGHT": 8,
  "NINE": 9,
  "TEN": 10,
  "ELEVEN": 11,
  "TWELVE": 12,
}

const arrows = {
  "ArrowLeft": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'LEFT'})),
  "ArrowRight": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'RIGHT'})),
  "ArrowUp": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'TOP'})),
  "ArrowDown": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'DOWN'}))
}

const roomId = document.location.href.split('/')[4];

const putHtml = (html) => {
  document.getElementById('room').innerHTML = html;
}

const stompClient = (() => {
  const socket = new SockJS("/websocket");
  const stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    stompClient.subscribe(`/topic/room/${roomId}`, (response) => {
      const rooms = JSON.parse(response.body);

      putHtml(createRoom(rooms));
    });
  });

  return stompClient;
})();

const startRoom = () => {
  stompClient.send(`/app/rooms/${roomId}/start`, {});
}

(async () => {
  const response = await axios.get(`/rooms/${roomId}`);
  const room = response.data;

  console.log(room);
  putHtml(createRoom(room));

  document.addEventListener("keydown", (e) => {
    arrows[e.key]();
  });
})();

const arrows = {
  "ArrowLeft": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'LEFT'})),
  "ArrowRight": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'RIGHT'})),
  "ArrowUp": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'TOP'})),
  "ArrowDown": () => stompClient.send(`/app/rooms/${roomId}/move`, {},
      JSON.stringify({direction: 'DOWN'}))
}
