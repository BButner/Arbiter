package com.bbutner.arbiter.api.authentication

import com.bbutner.arbiter.service.model.HarmonyUser
import com.bbutner.arbiter.service.model.HarmonyUserRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.server.DefaultServerRedirectStrategy
import org.springframework.security.web.server.ServerRedirectStrategy
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.net.URI

@Component
class AuthenticationSuccessHandler(
        val harmonyUserRepository: HarmonyUserRepository
): ServerAuthenticationSuccessHandler {
    private val redirectStrategy: ServerRedirectStrategy = DefaultServerRedirectStrategy()

    private val DEFAULT_LOGIN_SUCCESS_URL: String = "http://localhost:8080/users/22"

    override fun onAuthenticationSuccess(filter: WebFilterExchange, auth: Authentication): Mono<Void> = mono {
        val url: URI = URI.create(DEFAULT_LOGIN_SUCCESS_URL)
        val oAuth2User = auth.principal as DefaultOAuth2User

        if (oAuth2User.attributes.containsKey("email")) {
            val user: HarmonyUser? = harmonyUserRepository.getByEmail(oAuth2User.attributes["email"] as String)
            if (user != null) {
                filter.exchange.session.awaitSingle().attributes.putIfAbsent("HARMONY_USER_ID", user.id)
            } // else we need to create the user
        }

        redirectStrategy.sendRedirect(filter.exchange, url).awaitFirstOrNull()
    }
}