package tech.themukha.todo.tests.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import tech.themukha.todo.tests.config.TestConfig.BASE_URL
import tech.themukha.todo.tests.config.TestConfig.PORT
import tech.themukha.todo.tests.flow.TestFlow.Companion.logger
import tech.themukha.todo.tests.model.WebSocketDto
import tech.themukha.todo.tests.utils.JsonUtils.fromJson

class WebSocketManager(
    private val baseUrl: String = "$BASE_URL:$PORT/ws",
) {

    val webSocketClient by lazy { WebSocketManager() }

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    val receivedMessages = mutableListOf<WebSocketDto>()

    fun startConnection() {
        val request = Request.Builder()
            .url(baseUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                logger.debug("WebSocket connection opened")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val webSocketDto = fromJson<WebSocketDto>(text)
                receivedMessages.add(webSocketDto)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                logger.error("ReceiFved message from websocket as bytes: $bytes")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                logger.debug("WebSocket connection closing with code: $code, reason: $reason")
                webSocket.close(code, reason)
                receivedMessages.clear()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                logger.error("WebSocket connection failure: ${t.message}")
                throw t
            }

        })
    }

    fun closeConnection() {
        webSocket?.close(1000, "Closing connection")
    }

}