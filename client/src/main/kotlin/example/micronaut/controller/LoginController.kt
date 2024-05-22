package example.micronaut.controller

import io.micronaut.http.HttpHeaders.AUTHORIZATION
import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken

@Client("/login")
interface  LoginController {
    @Post
    fun login(@Body credentials: UsernamePasswordCredentials): BearerAccessRefreshToken

    @Consumes(TEXT_PLAIN)
    @Get
    fun home(@Header(AUTHORIZATION) authorization: String): String
}