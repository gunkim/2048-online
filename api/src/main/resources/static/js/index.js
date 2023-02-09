const createRooms = (rooms) => {
  if (rooms.length === 0) {
    return `
    <tr>
      <td colspan="3" style="text-align: center;">방이 없습니다.</td>
    </tr>
`;
  }
  return rooms.map(room => {
    return `
      <tr>
        <td>${room.id}></td>
        <td><a href="/rooms/${room.id}/details">${room.title}</a></td>
        <td>${room.host}</td>
      </tr>
      `;
  });
}

const putHtml = (html) => {
  document.getElementById('rooms').innerHTML = html;
}

const createRoom = async () => {
  const title = document.getElementById('create-title').value;

  const response = await axios.post("/rooms", {'title': title});

  if (response.status === 201) {
    const redirectUri = response.headers.location;
    window.location.href = redirectUri;
  }
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


