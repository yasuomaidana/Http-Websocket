package example.micronaut.controller.user.orders

import example.micronaut.controller.user.USER_BASE_ID
import example.micronaut.controller.user.User2Controller
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS

@Controller("$USER_BASE_ID/orders")
@Secured(IS_ANONYMOUS)
class OrderController: User2Controller() {
    override fun getAll(
        @PathVariable userId: Int,
        @QueryValue(defaultValue = "") sortBy: String,
        @QueryValue(defaultValue = "") sortOrder: String
    ):String = "All orders for user $userId"
}