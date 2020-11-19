package com.bbutner.arbiter.service.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserSettingsRepository: CoroutineCrudRepository<HarmonyUserSettings, Int> {
    suspend fun getByUserId(userId: Int): HarmonyUserSettings
}