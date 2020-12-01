package com.bbutner.arbiter.service.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user_setting")
class HarmonyUserSettings (
        @Id
        val id: Int,
        val userId: Int,
        val emailPublic: Boolean,
        val displayNamePublic: Boolean,
        val darkMode: Boolean
) {
    override fun toString(): String {
        return "[id=$id, userId=$userId, emailPublic=$emailPublic, displayNamePublic=$displayNamePublic, darkMode=$darkMode]"
    }
}