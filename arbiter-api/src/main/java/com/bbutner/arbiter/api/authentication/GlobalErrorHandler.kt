package com.bbutner.arbiter.api.authentication

import com.bbutner.arbiter.api.exception.spotify.SpotifyNotAuthenticatedException
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.withContext
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.nio.charset.StandardCharsets

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalErrorHandler(
        private val objectMapper: ObjectMapper
) : WebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> = mono {
        val (statusCode, errorResponse) = when(ex) {
            is AuthenticationCredentialsNotFoundException ->
                HttpStatus.UNAUTHORIZED to (401)
            is AuthenticationException ->
                HttpStatus.UNAUTHORIZED to (401)
            is AccessDeniedException ->
                HttpStatus.FORBIDDEN to (200)
            is SpotifyNotAuthenticatedException ->
                HttpStatus.UNAUTHORIZED to (401)

            else -> HttpStatus.INTERNAL_SERVER_ERROR to (0)
        }

        println(errorResponse)
        println(statusCode)
        println(ex)
        ex.printStackTrace()

        val response = exchange.response
        check(response.setStatusCode(statusCode)) { "HTTP response is committed" }
        response.headers.contentType = MediaType.APPLICATION_JSON

        @Suppress("BlockingMethodInNonBlockingContext")
        val json = withContext(Dispatchers.IO) { objectMapper.writeValueAsString(errorResponse) }
        val buffer = response.bufferFactory().wrap(json.toByteArray(StandardCharsets.UTF_8))

        response.writeWith(buffer.toMono())
                .doOnError { DataBufferUtils.release(buffer) }
                .subscribe()
    }.then()
}