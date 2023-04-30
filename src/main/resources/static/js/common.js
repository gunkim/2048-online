const createStompClient = (init) => {
    const socket = new SockJS("/websocket");
    const stompClient = Stomp.over(socket);

    init(stompClient);
    return stompClient;
};

const putHtml = (id, html) => {
    document.getElementById(id).innerHTML = html;
}
