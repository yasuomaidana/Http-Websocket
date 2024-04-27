package example.micronaut.service

import example.micronaut.mapper.PrintMapper
import example.micronaut.printers.PrintJob
import example.micronaut.printers.PrintJobRequest
import io.micronaut.websocket.WebSocketSession
import jakarta.inject.Singleton
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Singleton
class JobService(
    private val printerMapper:PrintMapper
){
    // ... Job Management ...
    private val webSocketSessions = mutableListOf<WebSocketSession>()
    private val jobs: MutableList<PrintJob> = mutableListOf()
    private var ids: Int = 0

    fun createJob(printJobRequest: PrintJobRequest, printerId: Int): PrintJob {
        val printJob = printerMapper.printJobRequestToPrintJob(printJobRequest, ids)
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

    fun broadcastUpdates() {
        val updates = jobs.map { job -> Json.encodeToString(job)}
        webSocketSessions.forEach { it.sendSync(updates) } // Assuming updates are serializable
    }
}