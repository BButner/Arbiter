package com.bbutner.arbiter.service.model.user

import org.springframework.data.annotation.Id

data class HarmonyUserSettingCategory(
        @Id
        val id: Int,
        val settingName: String,
        val description: String,
        val settingCategory: String
) {
    override fun toString(): String {
        return "[id=$id, settingName=$settingName, description=$description, settingCategory=$settingCategory]"
    }
}