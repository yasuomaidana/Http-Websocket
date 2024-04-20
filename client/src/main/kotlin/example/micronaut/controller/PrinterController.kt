package example.micronaut.controller

import example.micronaut.printers.Printer
import example.micronaut.service.PrinterService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/printer")
class PrinterController(private val printerService: PrinterService) {

    @Get("/{id}/status")
    fun getPrinterStatus(id: Int): Printer? {
        return printerService.getPrinterStatus(id)
    }
}