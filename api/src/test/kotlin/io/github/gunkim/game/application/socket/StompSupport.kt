package io.github.gunkim.game.application.socket

import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.util.concurrent.TimeUnit

private fun createTransport(): List<Transport> {
    val transports: MutableList<Transport> = ArrayList<Transport>(1)
    transports.add(WebSocketTransport(StandardWebSocketClient()))
    return transports
}

open class StompSupport {
    private val url: String = "ws://localhost"
    private val webSocketClient: WebSocketStompClient = WebSocketStompClient(
        SockJsClient(
            createTransport(),
        ),
    )

    init {
        webSocketClient.messageConverter = MappingJackson2MessageConverter()
    }

    protected fun connect(port: Int, stompHeaders: StompHeaders): StompSession = webSocketClient.connectAsync(
        "$url:$port/ws",
        WebSocketHttpHeaders(),
        stompHeaders,
        object : StompSessionHandlerAdapter() {},
    ).get(3, TimeUnit.SECONDS)

    protected fun createStompHeaders(token: String) = StompHeaders()
        .also { it.add("Authorization", token) }

    protected fun disconnect(stompSession: StompSession) {
        if (stompSession.isConnected) {
            stompSession.disconnect()
        }
    }
}
