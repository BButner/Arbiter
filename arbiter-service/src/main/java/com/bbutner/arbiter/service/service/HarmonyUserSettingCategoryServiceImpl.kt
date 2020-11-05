package com.bbutner.arbiter.service.service

import com.bbutner.arbiter.service.model.HarmonyUserSettingCategory
import com.bbutner.arbiter.service.model.HarmonyUserSettingCategoryRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class HarmonyUserSettingCategoryServiceImpl(
        private val harmonyUserSettingCategoryRepository: HarmonyUserSettingCategoryRepository
): HarmonyUserSettingCategoryService {
    override suspend fun findAll(): Flow<HarmonyUserSettingCategory> {
        return harmonyUserSettingCategoryRepository.findAll()
    }
}