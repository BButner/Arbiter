package com.bbutner.arbiter.api.controllers

import com.bbutner.arbiter.service.model.HarmonyUser
import com.bbutner.arbiter.service.model.HarmonyUserSettings
import com.bbutner.arbiter.service.service.HarmonyUserService
import com.bbutner.arbiter.service.service.HarmonyUserSettingsService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import java.lang.Exception

@CrossOrigin(origins = ["*"],
        allowedHeaders = ["*"],
        methods = [ RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.PUT ],
        allowCredentials = "true"
)
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('USER')")
class HarmonyUserController (
        private val harmonyUserService: HarmonyUserService,
        private val harmonyUserSettingsService: HarmonyUserSettingsService
) {
    @GetMapping("/{id}")
    suspend fun getUserById(@AuthenticationPrincipal user: OAuth2User, @PathVariable id: String, exchange: ServerWebExchange): HarmonyUser {
        try {
            return if (exchange.session.awaitSingle().attributes["HARMONY_USER_ID"].toString() == id) {
                harmonyUserService.getUserById(id)
            } else {
                val user: HarmonyUser = harmonyUserService.getUserById(id)
                val settings: HarmonyUserSettings = harmonyUserSettingsService.getUserSettingsByUserId(id)

                HarmonyUser(
                        user.id,
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
            return harmonyUserService.getUserById(exchange.session.awaitSingle().attributes["HARMONY_USER_ID"].toString())
        } catch (e: Exception) {
            throw e
        }
    }
}