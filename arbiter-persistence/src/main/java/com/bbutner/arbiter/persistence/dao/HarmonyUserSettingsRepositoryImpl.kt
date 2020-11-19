package com.bbutner.arbiter.persistence.dao

import com.bbutner.arbiter.service.model.HarmonyUserSettings
import com.bbutner.arbiter.service.model.HarmonyUserSettingsRepository
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserSettingsRepositoryImpl: HarmonyUserSettingsRepository, CoroutineCrudRepository<HarmonyUserSettings, Int> {
    @Query("select * from user_setting where user_id = ?")
    override suspend fun getByUserId(userId: Int): HarmonyUserSettings
}