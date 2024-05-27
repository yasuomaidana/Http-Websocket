package example.micronaut.printers

import kotlinx.serialization.Serializable

@Serializable
data class PrintJob(val id: Int, val name: String, var status: String = "pending", var totalTime: Int)