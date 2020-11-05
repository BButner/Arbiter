package com.bbutner.arbiter.service.service

import com.bbutner.arbiter.service.model.HarmonyUserSettings
import com.bbutner.arbiter.service.model.HarmonyUserSettingsRepository
import org.springframework.stereotype.Service

@Service
class HarmonyUserSettingsServiceImpl(
        private val harmonyUserSettingsRepository: HarmonyUserSettingsRepository
): HarmonyUserSettingsService {
    override suspend fun getUserSettingsByUserId(userId: Int): HarmonyUserSettings {
        return harmonyUserSettingsRepository.getByUserId(userId)
    }
}