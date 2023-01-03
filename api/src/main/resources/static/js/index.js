const createRooms = (rooms) => {
  return rooms.map(room => {
    return `
      <tr>
        <td><a href="/rooms/${room.id}/details">${room.id}</a></td>
        <td>${room.title}</td>
        <td>${room.host}</td>
        <td>${room.start}</td>
      </tr>
      `;
  });
}

const putHtml = (html) => {
  document.getElementById('rooms').innerHTML = html;
}

const createRoom = () => {
  const title = document.getElementById('create-title').value;

  stompClient.send('/app/room/create', {}, title);
}

const stompClient = (() => {
  const socket = new SockJS("/websocket");
  const stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    stompClient.subscribe('/topic/rooms', (response) => {
      const rooms = JSON.parse(response.body);

      putHtml(createRooms(rooms));
    });
  });

  return stompClient;
})();

(async () => {
  const response = await axios.get('/rooms');

  putHtml(createRooms(response.data));
})();


