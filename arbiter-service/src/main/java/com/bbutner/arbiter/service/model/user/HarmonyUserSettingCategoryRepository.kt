package com.bbutner.arbiter.service.model.user

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserSettingCategoryRepository {
    fun findAll(): Flow<HarmonyUserSettingCategory>
}