package example.micronaut.service

import example.micronaut.printers.Printer
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton

@Singleton
class PrinterService(
    private val jobService: JobService
) {
    private val printers: MutableMap<Int, Printer> = mutableMapOf(
        1 to Printer(1),
        2 to Printer(2),
        3 to Printer(3),
    )

    // For demo, a very basic status update
    @Scheduled(fixedDelay = "5s")
    fun simulatePrinterUpdates() {
        printers.values.forEach {
            if (it.status == "working") it.status = "free"
        }
    }

    fun getPrinterStatus(printerId: Int): Printer? = printers[printerId]
}