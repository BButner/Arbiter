package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.service.model.HarmonyUserRepository
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange

class LoginHelper {
    suspend fun handleLogin(harmonyUserRepository: HarmonyUserRepository, filter: WebFilterExchange, email: String) {
        AuthGenericHelper().storeUserIdInSession(filter.exchange.session.awaitSingle(), harmonyUserRepository.getIdByEmail(email))
    }

    fun authContainsEmailField(user: OAuth2User): Boolean {
        return user.attributes.containsKey("email")
    }

    suspend fun userRegisteredWithEmail(harmonyUserRepository: HarmonyUserRepository, email: String): Boolean {
        return harmonyUserRepository.getByEmail(email) != null
    }
}