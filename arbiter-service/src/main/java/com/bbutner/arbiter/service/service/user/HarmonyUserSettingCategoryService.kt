package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategory
import kotlinx.coroutines.flow.Flow

interface HarmonyUserSettingCategoryService {
    suspend fun findAll(): Flow<HarmonyUserSettingCategory>
}