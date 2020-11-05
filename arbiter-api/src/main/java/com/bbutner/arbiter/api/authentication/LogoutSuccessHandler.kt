package com.bbutner.arbiter.api.authentication

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.DefaultServerRedirectStrategy
import org.springframework.security.web.server.ServerRedirectStrategy
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.net.URI

@Component
class LogoutSuccessHandler(): ServerLogoutSuccessHandler {
    private val redirectStrategy: ServerRedirectStrategy = DefaultServerRedirectStrategy()
    private val DEFAULT_LOGOUT_SUCCESS_URL: String = "http://10.0.0.97:3000/"

    override fun onLogoutSuccess(filter: WebFilterExchange, auth: Authentication): Mono<Void> = mono {
        redirectStrategy.sendRedirect(filter.exchange, URI.create(DEFAULT_LOGOUT_SUCCESS_URL)).awaitFirstOrNull()
    }
}