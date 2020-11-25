package com.bbutner.arbiter.api.controllers

import com.bbutner.arbiter.api.exception.BadRequestException
import com.bbutner.arbiter.api.util.auth.SessionHelper
import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategory
import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategoryRepository
import com.bbutner.arbiter.service.model.user.HarmonyUserSettings
import com.bbutner.arbiter.service.model.user.HarmonyUserSettingsRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.server.awaitSession
import org.springframework.web.server.ServerWebExchange

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
            val idExternalFromSession: String? = SessionHelper().getUserIdExternalFromSession(exchange.awaitSession())

            return if (idExternalFromSession != null && idExternalFromSession == idExternal) {
                harmonyUserSettingsRepository.getByUserId(SessionHelper().getUserIdFromSession(exchange.awaitSession())!!)
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
            val idExternalFromSession: String? = SessionHelper().getUserIdExternalFromSession(exchange.awaitSession())

            return if (idExternalFromSession != null) {
                harmonyUserSettingsRepository.getByUserId(SessionHelper().getUserIdFromSession(exchange.awaitSession())!!)
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

    @PutMapping("/users/{idExternal}/settings")
    suspend fun updateUserSettingsByIdExternal(@AuthenticationPrincipal user: OAuth2User, @PathVariable idExternal: String, @RequestBody settings: HarmonyUserSettings, exchange: ServerWebExchange) {
        val settingsCurrent: HarmonyUserSettings = harmonyUserSettingsRepository.getByUserId(SessionHelper().getUserIdFromSession(exchange.awaitSession())!!)

        if (settingsCurrent.id == settings.id && settingsCurrent.userId == settings.userId) {
            println(settings)
            harmonyUserSettingsRepository.save(settings)
            return
        } else {
            throw BadRequestException()
        }
    }
}