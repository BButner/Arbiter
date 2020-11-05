package com.bbutner.arbiter.api.controllers

import com.bbutner.arbiter.api.util.auth.AuthGenericHelper
import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_USER_ID
import com.bbutner.arbiter.service.model.HarmonyUserSettingCategory
import com.bbutner.arbiter.service.model.HarmonyUserSettingCategoryRepository
import com.bbutner.arbiter.service.model.HarmonyUserSettings
import com.bbutner.arbiter.service.model.HarmonyUserSettingsRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.server.awaitSession
import org.springframework.web.server.ServerWebExchange
import java.util.*

@CrossOrigin(origins = ["*"],
        allowedHeaders = ["*"],
        methods = [ RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.PUT ],
        allowCredentials = "true"
)
@RestController
@PreAuthorize("hasRole('USER')")
class HarmonyUserSettingsController (
        private val harmonyUserSettingsRepository: HarmonyUserSettingsRepository,
        private val harmonyUserSettingCategoryRepository: HarmonyUserSettingCategoryRepository
) {
    @GetMapping("/users/{idExternal}/settings")
    suspend fun getUserSettingsById(@AuthenticationPrincipal user: OAuth2User, @PathVariable idExternal: String, exchange: ServerWebExchange): HarmonyUserSettings {
        try {
            val idExternalFromSession: String? = AuthGenericHelper().getUserIdExternalFromSession(exchange.awaitSession())

            return if (idExternalFromSession != null && idExternalFromSession == idExternal) {
                harmonyUserSettingsRepository.getByUserId(AuthGenericHelper().getUserIdFromSession(exchange.awaitSession())!!)
            } else {
                throw AuthenticationCredentialsNotFoundException("Not Authenticated")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping("/users/me/settings")
    suspend fun getUserSettingsImplicit(@AuthenticationPrincipal user: OAuth2User, exchange: ServerWebExchange): HarmonyUserSettings {
        try {
            val idExternalFromSession: String? = AuthGenericHelper().getUserIdExternalFromSession(exchange.awaitSession())

            return if (idExternalFromSession != null) {
                harmonyUserSettingsRepository.getByUserId(AuthGenericHelper().getUserIdFromSession(exchange.awaitSession())!!)
            } else {
                throw AuthenticationCredentialsNotFoundException("Not Authenticated")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping("/settings/categories")
    suspend fun getSettingCategories(@AuthenticationPrincipal user: OAuth2User, exchange: ServerWebExchange): Flow<HarmonyUserSettingCategory> {
        try {
            return harmonyUserSettingCategoryRepository.findAll()
        } catch (e: Exception) {
            throw e
        }
    }
}