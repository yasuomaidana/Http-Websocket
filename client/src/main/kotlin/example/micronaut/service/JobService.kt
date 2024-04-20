package example.micronaut.service

import io.micronaut.websocket.WebSocketSession
import jakarta.inject.Singleton

@Singleton
class JobService(){
    // ... Job Management ...
    private val webSocketSessions = mutableListOf<WebSocketSession>()

    fun addWebSocketSession(session: WebSocketSession) {
        webSocketSessions.add(session)
    }

    fun removeWebSocketSession(session: WebSocketSession){
        webSocketSessions.remove(session)
    }
    // ... removeWebSocketSession ...

    fun broadcastUpdates(jobUpdates: String) { // Assuming JSON string
        webSocketSessions.forEach { it.sendSync(jobUpdates) }
    }
}