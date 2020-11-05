package com.bbutner.arbiter.service.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

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
        return String.format("[id=%s, idExternal=%s, avatarUrl=%s, username=%s, displayName=%s, email=%s, registrationDate=%s]",
            id, idExternal, avatarUrl, username, displayName, email, registrationDate)
    }
}