package example.micronaut.printers

data class Printer(val id: String, var status: String = "free")
data class PrintJob(val id: String, val name: String, var status: String = "pending", val totalTime: Int)
