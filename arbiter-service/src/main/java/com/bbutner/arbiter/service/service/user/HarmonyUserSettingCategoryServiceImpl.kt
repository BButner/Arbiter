package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategory
import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategoryRepository
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