package example.micronaut.handler

import io.micronaut.context.annotation.Requires
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import java.sql.SQLException

@Produces
@Singleton
@Requires(classes = [DataAccessException::class, ExceptionHandler::class])
class DataAccessExceptionExceptionHandler:ExceptionHandler<DataAccessException, HttpResponse<Any>> {
    override fun handle(request: HttpRequest<*>, exception: DataAccessException): HttpResponse<Any> {

        // If it's a duplicate entry exception, return a custom response
        if (exception.cause is SQLException && (exception.cause as SQLException).message?.contains("duplicate", true)!!) {
            return HttpResponse.status<Any>(HttpStatus.CONFLICT).body(mapOf("message" to "Duplicate entry violation"))
        }

        // Optionally, use your custom exception class
        throw exception
    }
}