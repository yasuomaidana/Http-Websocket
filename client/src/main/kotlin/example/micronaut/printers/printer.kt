package example.micronaut.printers

data class Printer(val id: Int, var status: String = "free")
data class PrintJob(var id: Int, val name: String, var status: String = "pending", var totalTime: Int)
