package example.micronaut.graphql.handler

import example.micronaut.exception.ForbiddenException
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
    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters?): CompletableFuture<DataFetcherExceptionHandlerResult> {
        if (handlerParameters?.exception is ForbiddenException) {
            val exception = handlerParameters.exception as ForbiddenException
            logger.error(exception.message)
            val error = GraphqlErrorBuilder.newError()
                .message(exception.message)
                .build()
//            return CompletableFuture.failedFuture(exception)
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build())
        } else {
            throw handlerParameters?.exception!! // rethrow the exception if it's not an UnauthorizedException or custom supported error
        }
    }
}