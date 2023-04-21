const createRooms = (rooms) => {
  return rooms.map(room => {
    return `
      <div class="room-card" onclick="joinRoom('${room.id}');" style="cursor: pointer;">
        <div class="room-name">${room.title}</div>
        <span class="badge">${room.currentPlayer}/${room.maxPlayer}</span>
      </div>
      `;
  });
}

const joinRoom = async (roomId) => {
  const response = await axios.post(`/rooms/${roomId}/join`);
  if(response.status !== 200) {
    alert('방에 입장할 수 없습니다.');
    return;
  }
  window.location.href = `/waitroom/${roomId}`;
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


