package com.bbutner.arbiter.api.controllers

import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_REGISTER_USERNAME
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.web.server.DefaultServerRedirectStrategy
import org.springframework.security.web.server.ServerRedirectStrategy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import java.net.URI

@RestController
@RequestMapping("/register")
class RegisterController {
    private val redirectStrategy: ServerRedirectStrategy = DefaultServerRedirectStrategy()

    @GetMapping("")
    suspend fun handleRegister(@RequestParam("serviceId") serviceId: String, @RequestParam("username") username: String, exchange: ServerWebExchange) {
        exchange.session.awaitSingle().attributes.putIfAbsent(SESSION_HARMONY_REGISTER_USERNAME, username)

        redirectStrategy.sendRedirect(exchange, URI("/login/oauth2/authorization/$serviceId")).awaitFirstOrNull()
    }
}