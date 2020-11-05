package com.bbutner.arbiter.api.authentication

import com.bbutner.arbiter.api.util.auth.LoginHelper
import com.bbutner.arbiter.api.util.auth.RegistrationHelper
import com.bbutner.arbiter.service.model.HarmonyUserRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.server.DefaultServerRedirectStrategy
import org.springframework.security.web.server.ServerRedirectStrategy
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.reactive.server.awaitSession
import reactor.core.publisher.Mono
import java.net.URI

@Component
class AuthenticationSuccessHandler(
        val harmonyUserRepository: HarmonyUserRepository
): ServerAuthenticationSuccessHandler {
    private val redirectStrategy: ServerRedirectStrategy = DefaultServerRedirectStrategy()

    private val DEFAULT_LOGIN_SUCCESS_URL: String = "http://10.0.0.97:3000/"

    override fun onAuthenticationSuccess(filter: WebFilterExchange, auth: Authentication): Mono<Void> = mono {
        val url: URI = URI.create(DEFAULT_LOGIN_SUCCESS_URL)
        val oAuth2User = auth.principal as DefaultOAuth2User

        if (LoginHelper().authContainsEmailField(oAuth2User)) {
            val email = oAuth2User.attributes["email"] as String

            if (LoginHelper().userRegisteredWithEmail(harmonyUserRepository, email)) {
                LoginHelper().handleLogin(harmonyUserRepository, filter, email)
            } else {
                RegistrationHelper().handleRegistration(
                        harmonyUserRepository,
                        filter.exchange.awaitSession(),
                        email,
                        oAuth2User.attributes["display_name"] as String // TODO Make sure that we convert this into a function, as display_name is different from one provider to another
                )
            }
        }

        redirectStrategy.sendRedirect(filter.exchange, url).awaitFirstOrNull()
    }
}