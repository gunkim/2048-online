const joinRoom = async (roomId) => {
  const response = await axios.post(`/rooms/${roomId}/join`);
  if(response.status !== 200) {
    alert('방에 입장할 수 없습니다.');
    return;
  }
  window.location.href = `/waitroom/${roomId}`;
}

const createRoom = async (title) => {
  const response = await axios.post("/rooms", {'title': title});

  if (response.status === 201) {
    const redirectUri = response.headers.location;
    window.location.href = redirectUri;
  }
}

const createRooms = (rooms) => {
  return rooms.map(room => {
    return `
      <div class="room-card" onclick="joinRoom('${room.id}');">
        <div class="room-name">${room.title}</div>
        <span class="badge">${room.currentPlayer}/${room.maxPlayer}</span>
      </div>
      `;
  }).join('');
}


const stompClient = createStompClient((stompClient) => {
  stompClient.connect({}, () => {
    stompClient.subscribe('/topic/rooms', (response) => {
      const rooms = JSON.parse(response.body);

      putHtml('room-list', createRooms(rooms));
    });
  });
});

(async () => {
  const response = await axios.get('/rooms');

  putHtml('room-list', createRooms(response.data));
})();


