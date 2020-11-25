package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.model.user.HarmonyUserSettings

interface HarmonyUserSettingsService {
    suspend fun getUserSettingsByUserId(userId: Int): HarmonyUserSettings
}