package example.micronaut.service

import example.micronaut.printers.Printer
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton

@Singleton
class PrinterService {
    private val printers: MutableMap<String, Printer> = mutableMapOf(
        "1" to Printer("printer_1"),
        // ... more printers
    )

    // For demo, a very basic status update
    @Scheduled(fixedDelay = "5s")
    fun simulatePrinterUpdates() {
        printers.values.forEach {
            if (it.status == "working") it.status = "free"
        }
    }

    fun getPrinterStatus(printerId: String): Printer? = printers[printerId]
}