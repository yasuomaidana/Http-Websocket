package example.micronaut.service

import example.micronaut.printers.PrintJob
import example.micronaut.printers.PrintJobRequest
import io.micronaut.websocket.WebSocketSession
import jakarta.inject.Singleton

@Singleton
class JobService(
){
    // ... Job Management ...
    private val webSocketSessions = mutableListOf<WebSocketSession>()
    private val jobs: MutableList<PrintJob> = mutableListOf()
    private var ids: Int = 0

    fun createJob(printJobRequest: PrintJobRequest, printerId: Int): PrintJob {
        val printJob = PrintJob.toPrintJob(printJobRequest,ids)
        printJob.status = "printing"
        ids ++
        jobs.add(printJob)
        return printJob
    }

    fun removeJob(){
        jobs.removeAt(0)
    }

    fun addWebSocketSession(session: WebSocketSession) {
        webSocketSessions.add(session)
    }

    fun removeWebSocketSession(session: WebSocketSession){
        webSocketSessions.remove(session)
    }

    fun broadcastUpdates() { // Assuming JSON string
        val updates = jobs.map { job -> mapOf("id" to job.id, "status" to job.status) } // Prepare a suitable update object for WebSocket }
        webSocketSessions.forEach { it.sendSync(updates) } // Assuming updates are serializable
    }
}