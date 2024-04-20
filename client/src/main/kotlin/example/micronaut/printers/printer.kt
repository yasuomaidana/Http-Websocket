package example.micronaut.printers

data class Printer(val id: Int, var status: String = "free")
data class PrintJob(val id: Int, val name: String, var status: String = "pending", val totalTime: Int)
