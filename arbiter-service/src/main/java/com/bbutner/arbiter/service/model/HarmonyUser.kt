package com.bbutner.arbiter.service.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user")
data class HarmonyUser(
        @Id
        val id: String,
        val avatarUrl: String,
        val username: String,
        val displayName: String?,
        val email: String?,
        val registrationDate: LocalDateTime
) {
    override fun toString(): String {
        return String.format("[id=%s, avatarUrl=%s, username=%s, displayName=%s, email=%s, registrationDate=%s]",
            id, avatarUrl, username, displayName, email, registrationDate)
    }
}