package com.bbutner.arbiter.api.controllers

import com.bbutner.arbiter.api.util.auth.SessionHelper
import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_USER_UUID
import com.bbutner.arbiter.service.model.user.HarmonyUser
import com.bbutner.arbiter.service.model.user.HarmonyUserSettings
import com.bbutner.arbiter.service.service.user.HarmonyUserService
import com.bbutner.arbiter.service.service.user.HarmonyUserSettingsService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.server.awaitSession
import org.springframework.web.server.ServerWebExchange
import java.lang.Exception
import java.util.*

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('USER')")
class HarmonyUserController (
        private val harmonyUserService: HarmonyUserService,
        private val harmonyUserSettingsService: HarmonyUserSettingsService
) {
    @GetMapping("/{idExternal}")
    suspend fun getUserById(@AuthenticationPrincipal user: OAuth2User, @PathVariable idExternal: String, exchange: ServerWebExchange): HarmonyUser {
        try {
            val user: HarmonyUser = harmonyUserService.getUserByUuid(idExternal)
            val userId: UUID? = SessionHelper().getUserUuidFromSession(exchange.awaitSession())

            return if (userId != null && userId == user.uuid) {
                user
            } else {
                val settings: HarmonyUserSettings = harmonyUserSettingsService.getUserSettingsByUserId(user.uuid.toString()!!)

                HarmonyUser(
                        user.uuid,
                        user.avatarUrl,
                        user.username,
                        if (settings.displayNamePublic) user.displayName else null,
                        if (settings.emailPublic) user.email else null,
                        user.registrationDate
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping("/me")
    suspend fun getUserImplicit(@AuthenticationPrincipal user: OAuth2User, exchange: ServerWebExchange): HarmonyUser {
        try {
            return harmonyUserService.getUserByUuid(exchange.session.awaitSingle().attributes[SESSION_HARMONY_USER_UUID].toString())
        } catch (e: Exception) {
            throw e
        }
    }
}