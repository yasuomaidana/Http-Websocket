package example.micronaut.mapper

import example.micronaut.printers.PrintJob
import example.micronaut.printers.PrintJobRequest
import jakarta.inject.Singleton
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
@Singleton
interface PrintMapper {
    @Mapping(target = "status", ignore = true)
    fun printJobRequestToPrintJob(printJobRequest:PrintJobRequest, id:Int):PrintJob
}