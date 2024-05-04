package example.micronaut.printers

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import kotlinx.serialization.Serializable

data class Printer(val id: Int, var status: String = "free")

@Introspected
@Serdeable
data class PrintJobRequest(val name: String, val totalTime: Int)

@Serializable
data class PrintJob(val id: Int, val name: String, var status: String = "pending", var totalTime: Int)
