package com.bbutner.arbiter.persistence.dao

import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategory
import com.bbutner.arbiter.service.model.user.HarmonyUserSettingCategoryRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserSettingCategoryRepositoryImpl: HarmonyUserSettingCategoryRepository, CoroutineCrudRepository<HarmonyUserSettingCategory, String> {
    @Query("""
        select user_setting_lang.id, user_setting_lang.setting_name, user_setting_lang.description, user_setting_category.setting_category 
        from user_setting_lang 
        inner join user_setting_category on user_setting_lang.category_id = user_setting_category.id
    """)
    override fun findAll(): Flow<HarmonyUserSettingCategory>
}