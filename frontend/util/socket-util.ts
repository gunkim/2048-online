import SockJS from "sockjs-client"
import Stomp from "stompjs"

const sockJS = new SockJS("http://localhost:8080/webSocket")
const stompClient: Stomp.Client = Stomp.over(sockJS)
stompClient.debug = () => {}

export default stompClient
