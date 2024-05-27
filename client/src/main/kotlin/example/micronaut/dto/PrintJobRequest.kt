package example.micronaut.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class PrintJobRequest(val name: String, val totalTime: Int)