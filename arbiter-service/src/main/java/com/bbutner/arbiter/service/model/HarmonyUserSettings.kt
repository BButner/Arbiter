package com.bbutner.arbiter.service.model

import org.springframework.data.annotation.Id

class HarmonyUserSettings (
        @Id
        val id: Int,
        val userId: Int,
        val emailPublic: Boolean,
        val displayNamePublic: Boolean,
        val darkMode: Boolean
) {
    override fun toString(): String {
        return String.format("[id=%s, userId=%s, emailPublic=%s, displayNamePublic=%s, darkMode=%s",
            id, userId, emailPublic, displayNamePublic, darkMode)
    }
}