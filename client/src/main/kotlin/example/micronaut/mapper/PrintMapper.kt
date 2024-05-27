package example.micronaut.mapper

import example.micronaut.printers.PrintJob
import example.micronaut.dto.PrintJobRequest

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named


@Mapper(componentModel = "jakarta")
interface PrintMapper {

    @Mapping(source = "printJobRequest", target = "status" ,qualifiedByName = ["defaultStatus"])
    fun printJobRequestToPrintJob(printJobRequest: PrintJobRequest, id:Int):PrintJob

    fun printJobRequestToPrintJob(printJobRequest: PrintJobRequest, id:Int, status:String):PrintJob

    @Named("defaultStatus")
    fun defaultStatus(printJobRequest: PrintJobRequest):String {
        return "pending"
    }
}