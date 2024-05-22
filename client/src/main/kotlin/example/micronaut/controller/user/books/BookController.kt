package example.micronaut.controller.user.books

import example.micronaut.controller.user.USER_BASE_ID
import example.micronaut.controller.user.User2Controller
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS

@Controller("$USER_BASE_ID/books")
@Secured(IS_ANONYMOUS)
class BookController: User2Controller() {
    override fun getAll(userId: Int): String = "All books for user $userId"
}