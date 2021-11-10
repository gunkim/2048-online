import SockJS from "sockjs-client"
import Stomp from "stompjs"

const sockJS = new SockJS(`${process.env.NEXT_PUBLIC_SERVER_IP}/webSocket`)
const stompClient: Stomp.Client = Stomp.over(sockJS)
stompClient.debug = () => {}

export default stompClient
