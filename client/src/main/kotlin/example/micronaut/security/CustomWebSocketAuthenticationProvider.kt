package example.micronaut.security



import example.micronaut.repository.UserRepository
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse.status
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import org.reactivestreams.Publisher

@Filter("/jobs/status")
class CustomWebSocketAuthenticationProvider: HttpServerFilter {

    override fun doFilter(request: HttpRequest<*>?, chain: ServerFilterChain?): Publisher<MutableHttpResponse<*>> {
        if (request == null || chain == null) {
            return Publishers.just(status<MutableHttpResponse<*>>(HttpStatus.BAD_REQUEST))
        }
        return chain.proceed(request)

    }
}
