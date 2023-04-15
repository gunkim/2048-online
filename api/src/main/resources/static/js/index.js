const createRooms = (rooms) => {
  return rooms.map(room => {
    return `
      <a href="/waitroom/${room.id}">
        <div class="room-card">
          <div class="room-name">${room.title}</div>
          <span class="badge">1/4</span>
        </div>
      </a>
      `;
  });
}

const putHtml = (html) => {
  document.getElementById('room-list').innerHTML = html;
}

const createRoom = async () => {
  const title = document.getElementById('room-name').value;

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


