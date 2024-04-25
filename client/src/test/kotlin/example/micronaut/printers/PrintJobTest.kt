package example.micronaut.printers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PrintJobTest{


    @Test
    fun `test manual mapping`() {
        val request = PrintJobRequest(name = "Job Name", totalTime = 100)
        val mappedJob = PrintJob.toPrintJob(request, 0)
        assertEquals("Job Name", mappedJob.name)
        assertEquals(0, mappedJob.id)  // Since id is ignored
        assertEquals("pending", mappedJob.status)
        assertEquals(100, mappedJob.totalTime)
    }

}