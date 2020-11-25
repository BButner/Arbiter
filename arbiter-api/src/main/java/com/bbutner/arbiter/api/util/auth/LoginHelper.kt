package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.service.model.user.HarmonyUser
import com.bbutner.arbiter.service.model.user.HarmonyUserRepository
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.web.reactive.server.awaitSession

class LoginHelper {
    suspend fun handleLogin(harmonyUserRepository: HarmonyUserRepository, filter: WebFilterExchange, email: String) {
        val user: HarmonyUser? = harmonyUserRepository.getByEmail(email)

        if (user != null) {
            SessionHelper().storeUserIdInSession(filter.exchange.session.awaitSingle(), user.id!!)
            SessionHelper().storeUserIdExternalInSession(filter.exchange.awaitSession(), user.idExternal)
        }
    }

    fun authContainsEmailField(user: OAuth2User): Boolean {
        return user.attributes.containsKey("email")
    }

    suspend fun userRegisteredWithEmail(harmonyUserRepository: HarmonyUserRepository, email: String): Boolean {
        return harmonyUserRepository.getByEmail(email) != null
    }
}