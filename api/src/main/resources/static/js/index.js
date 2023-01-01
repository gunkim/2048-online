const socket = new SockJS("/websocket");
const stompClient = Stomp.over(socket);

stompClient.connect({}, (frame) => {
  console.log('Connected: ' + frame);

  stompClient.subscribe('/topic/rooms', (response) => {
    console.log("send ===>")
    const rooms = JSON.parse(response.body);

    let body = '';
    for (const room of rooms) {
      const roomLine = `
      <tr>
        <td>${room.id}</td>
        <td>${room.title}</td>
        <td>${room.host}</td>
        <td>${room.start}</td>
      </tr>
      `;
      body += roomLine;
    }
    document.getElementById('rooms').innerHTML = body;
  });
});

const createRoom = (e) => {
  console.log("createRoom ===>");
  const title = document.getElementById('create-title').value;

  stompClient.send('/app/room/create', {}, title);
}
