package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.model.user.HarmonyUserSettings
import com.bbutner.arbiter.service.model.user.HarmonyUserSettingsRepository
import org.springframework.stereotype.Service

@Service
class HarmonyUserSettingsServiceImpl(
        private val harmonyUserSettingsRepository: HarmonyUserSettingsRepository
): HarmonyUserSettingsService {
    override suspend fun getUserSettingsByUserId(userId: Int): HarmonyUserSettings {
        return harmonyUserSettingsRepository.getByUserId(userId)
    }
}