package example.micronaut.printers

import kotlinx.serialization.Serializable

data class Printer(val id: Int, var status: String = "free")
data class PrintJobRequest(val name: String, val totalTime: Int)

@Serializable
data class PrintJob(val id: Int, val name: String, var status: String = "pending", var totalTime: Int)
