package com.bbutner.arbiter.service.model

import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserSettingsRepository {
    suspend fun getByUserId(userId: Int): HarmonyUserSettings
}