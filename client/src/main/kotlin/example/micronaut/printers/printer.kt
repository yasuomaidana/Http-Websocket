package example.micronaut.printers

data class Printer(val id: Int, var status: String = "free")
data class PrintJobRequest(val name: String, val totalTime: Int)
data class PrintJob(val id: Int, val name: String, var status: String = "pending", var totalTime: Int){
    companion object {
        fun toPrintJob(printJobRequest: PrintJobRequest,id: Int) =
            PrintJob(id=id,name=printJobRequest.name, totalTime = printJobRequest.totalTime)
    }
}
