package example.micronaut.controller

import example.micronaut.printers.PrintJob
import example.micronaut.service.PrinterService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.views.View

import java.time.LocalDateTime

@Controller("/job")
class PrintJobController(private val printerService: PrinterService) {

    // ... printer status endpoints ...

    @Post
    fun submitPrintJob(@Body printJob: PrintJob): PrintJobResponse {
        // TODO:
        // 1. Validate printJob
        // 2. Find available printer (check printerService)
        // 3. If available, assign job to printer, update status, return success response
        // 4. If not, schedule the job and return scheduled times

        return PrintJobResponse("success", "Job submitted",
                                startTime = LocalDateTime.now(),
                                estimatedFinishTime = LocalDateTime.now().plusMinutes(5)) // Placeholder
    }

    @Get
    @View("job/Printing Jobs.html")
    fun jobsPage(){}
}

data class PrintJobResponse(val status: String, val message: String, val startTime: LocalDateTime? = null, val estimatedFinishTime: LocalDateTime? = null)