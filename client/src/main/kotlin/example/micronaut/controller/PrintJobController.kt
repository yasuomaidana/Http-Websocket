package example.micronaut.controller

import example.micronaut.printers.PrintJob
import example.micronaut.service.JobService
import example.micronaut.service.PrinterService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.views.View

import java.time.LocalDateTime

@Controller("/job")
class PrintJobController(
    private val printerService: PrinterService,
    private val jobService: JobService
) {

    // ... printer status endpoints ...

    @Post
    fun submitPrintJob(@Body printJob: PrintJob): PrintJobResponse {
        // TODO:
        // 1. Validate printJob

         // 2. Find available printer
        val availablePrinter = printerService.findFreePrinter()

        return if (availablePrinter != null) {
            // 3. If available, assign job to printer
            availablePrinter.status = "working"
            printerService.setBusy(availablePrinter.id)
            val job = jobService.createJob(printJob, availablePrinter.id)

            val startTime =  LocalDateTime.now()
            val endTime = startTime.plusSeconds(printJob.totalTime.toLong())
            // Broadcast the update over WebSocket
            jobService.broadcastUpdates()


            PrintJobResponse("success", "Job submitted", startTime = startTime, estimatedFinishTime = endTime)
        } else {
            // 4. If not, schedule the job
            // ... (For now, let's just return a pending status)
            PrintJobResponse("pending", "No printers available")
        }

    }

    @Get
    @View("job/Printing Jobs.html")
    fun jobsPage(){}

    @Get("/temperature")
    @View("job/Printing Jobs.html")
    fun jobsTemperaturePage(){}
}

data class PrintJobResponse(val status: String, val message: String, val startTime: LocalDateTime? = null, val estimatedFinishTime: LocalDateTime? = null)