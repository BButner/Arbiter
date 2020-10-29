package com.bbutner.arbiter.service.service

import com.bbutner.arbiter.service.model.HarmonyUserSettings

interface HarmonyUserSettingsService {
    suspend fun getUserSettingsByUserId(userId: String): HarmonyUserSettings
}