const level = {
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

const getMap2 = (row) => row.content.map(cell => `
<td class="${cell}" style="border: 1px solid black; width: 100px; height: 100px;">
    ${(2 ** level[cell]) === 1 ? 0 : 2 ** level[cell]}
</td>
`).join('');

const getMap1 = (gamer) => gamer.board.rows.content.map(row => `
<tr>
    ${(getMap2(row))}
</tr>
`).join('');

const getMap = (room) => room.gamers.map(gamer => `
<td>
    <table>
        ${(getMap1(gamer))}
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
            ${(getMap(room))}
        </tr>
    </tbody>
</table>
`;

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
  stompClient.send(`/app/room/${roomId}/start`, {});
}

(async () => {
  const response = await axios.get(`/rooms/${roomId}`);
  const room = response.data;

  console.log(room);
  putHtml(createRoom(room));

  document.addEventListener("keydown", function (e) {
    if (e.key === 'ArrowLeft') {
      stompClient.send(`/app/rooms/${roomId}/move/left`, {});
    } else if (e.key === 'ArrowRight') {
      stompClient.send(`/app/rooms/${roomId}/move/right`, {});
    } else if (e.key === 'ArrowUp') {
      stompClient.send(`/app/rooms/${roomId}/move/top`, {});
    } else if (e.key === 'ArrowDown') {
      stompClient.send(`/app/rooms/${roomId}/move/down`, {});
    }
  });
})();
