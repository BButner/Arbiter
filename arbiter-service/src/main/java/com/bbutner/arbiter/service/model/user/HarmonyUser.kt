package com.bbutner.arbiter.service.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user")
data class HarmonyUser(
        @Id
        val id: Int?,
        val idExternal: String,
        val avatarUrl: String,
        val username: String,
        val displayName: String?,
        val email: String?,
        val registrationDate: LocalDateTime
) {
    override fun toString(): String {
        return "[id=%s, idExternal=$idExternal, avatarUrl=$avatarUrl, username=$username, displayName=$displayName, email=$email, registrationDate=$registrationDate]"
    }
}