const createRoom = (room) => {
  return `
    <h2>번호 : ${room.id}</h2>
    <h2>방제 : ${room.title}</h2>
    <h2>시작 여부 : ${room.start}</h2>
    
    <table>
      <tbody>
        <tr>
            ${room.gamers.map(gamer => {
              return `
                <td>
                    <table>
                        ${gamer.board.rows.content.map(row => {
                          return `
                          <tr>
                            ${row.content.map(cell => {
                            return `<td>
                              ${cell}
                            </td>`
                          })}
                          </tr>`
                        })}
                    </table>
                </td>
              `;
            })}
        </tr>
      </tbody>
    </table>
    `;
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
  stompClient.send(`/app/room/${roomId}/start`, {});
}

(async () => {
  const response = await axios.get(`/rooms/${roomId}`);
  const room = response.data;

  console.log(room);
  putHtml(createRoom(room));

  document.addEventListener("keydown", function (e) {
    if(e.key === 'ArrowLeft') {
      stompClient.send(`/app/rooms/${roomId}/move/left`, {});
    } else if(e.key === 'ArrowRight') {
      stompClient.send(`/app/rooms/${roomId}/move/right`, {});
    } else if(e.key === 'ArrowUp') {
      stompClient.send(`/app/rooms/${roomId}/move/top`, {});
    } else if(e.key === 'ArrowDown') {
      stompClient.send(`/app/rooms/${roomId}/move/down`, {});
    }
  });
})();
