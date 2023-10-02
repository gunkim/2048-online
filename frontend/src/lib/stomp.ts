import Stomp, {Client} from "stompjs";

export const stompClient = (): Client => {
    // @ts-ignore
    const sockjs = new SockJS("/api/websocket");
    return Stomp.over(sockjs);
}
