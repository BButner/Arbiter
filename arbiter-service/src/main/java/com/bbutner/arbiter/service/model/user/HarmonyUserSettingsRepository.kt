package com.bbutner.arbiter.service.model.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserSettingsRepository: CoroutineCrudRepository<HarmonyUserSettings, Int> {
    suspend fun getByUserId(userId: String): HarmonyUserSettings
}