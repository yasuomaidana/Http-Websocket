package example.micronaut.graphql.handler

import example.micronaut.exception.ForbiddenException
import example.micronaut.exception.NotFoundException
import example.micronaut.exception.NullIdException
import example.micronaut.exception.UserNotAuthenticatedException
import graphql.GraphqlErrorBuilder
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

@Singleton
class CustomDataFetcherExceptionHandler:DataFetcherExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(CustomDataFetcherExceptionHandler::class.java)
    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        return buildErrorMessage(handlerParameters.exception as Exception) ?: throw handlerParameters.exception
    }

    private fun buildErrorMessage(exception: Exception): CompletableFuture<DataFetcherExceptionHandlerResult>? {
        val supportedErrors = listOf(ForbiddenException::class.java,
            NotFoundException::class.java, NullIdException::class.java,
            UserNotAuthenticatedException::class.java)
        if (supportedErrors.contains((exception.cause ?: exception)::class.java)) {
            logger.error(exception.message)
            val error = GraphqlErrorBuilder.newError()
                .message(exception.message)
                .build()
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build())
        } else {
            return null
        }
    }
}