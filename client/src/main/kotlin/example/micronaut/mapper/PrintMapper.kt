package example.micronaut.mapper

import example.micronaut.printers.PrintJob
import example.micronaut.printers.PrintJobRequest
import org.mapstruct.Mapper
//import org.mapstruct.Named


@Mapper(componentModel = "jsr330")
interface PrintMapper {
//    @Mapping(target = "status", ignore = true)
    fun printJobRequestToPrintJob(printJobRequest:PrintJobRequest, id:Int, status:String="pending"):PrintJob
}