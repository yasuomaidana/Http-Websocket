package example.micronaut.controller.user

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue

const val USER_ID = "userId"
const val USER_BASE_ID = "/users/{$USER_ID}"

abstract class User2Controller{
    @Get
    abstract fun getAll(
        @PathVariable userId: Int,
        @QueryValue(defaultValue = "") sortBy: String,
        @QueryValue(defaultValue = "") sortOrder: String
    ):Any
}