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
    @Scheduled(fixedDelay = "15s")
    fun simulatePrinterUpdates() {
        val busyPrinter = printers.values.firstOrNull { it.status == "working" }
        if (busyPrinter != null) {
            busyPrinter.status = "free"
            jobService.removeJob()
            jobService.broadcastUpdates()
        }
    }
    fun setBusy(id:Int){
        val printer = printers[id]
        if(printer?.status=="free"){
            printer.status = "working"
        }

    }
    fun findFreePrinter(): Printer? {
        return printers.values.firstOrNull{ it.status == "free" }
    }

    fun getPrinterStatus(printerId: Int): Printer? = printers[printerId]
}