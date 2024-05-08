package example.micronaut.websocket

import example.micronaut.service.JobService
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket

@Secured
@ServerWebSocket("/jobs/status")
class PrintJobStatusWebSocket(private val jobService: JobService) { // Assuming a JobService to track jobs

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @OnOpen
    fun onOpen(session: WebSocketSession) {
        jobService.addWebSocketSession(session)
    }

    @OnMessage
    fun onMessage(message: String, session: WebSocketSession) {
        // ... handle client requests if needed
    }

    // ... other handlers ...

    @OnClose
    fun onClose(session: WebSocketSession) {
        jobService.removeWebSocketSession(session)
    }

    // The JobService would broadcast status updates to this WebSocket periodically
}