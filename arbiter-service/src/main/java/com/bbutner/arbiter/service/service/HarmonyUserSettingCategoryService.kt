package com.bbutner.arbiter.service.service

import com.bbutner.arbiter.service.model.HarmonyUserSettingCategory
import kotlinx.coroutines.flow.Flow

interface HarmonyUserSettingCategoryService {
    suspend fun findAll(): Flow<HarmonyUserSettingCategory>
}