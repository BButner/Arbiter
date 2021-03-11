package com.bbutner.arbiter.service.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("user")
data class HarmonyUser(
    @Id
    val uuid: UUID?,
    val avatarUrl: String,
    val username: String,
    val displayName: String?,
    val email: String?,
    val registrationDate: LocalDateTime,
    @org.springframework.data.annotation.Transient
    val insert: Boolean = false
): Persistable<String> {
    @PersistenceConstructor
    constructor(
        uuid: UUID?,
        avatarUrl: String,
        username: String,
        displayName: String?,
        email: String?,
        registrationDate: LocalDateTime
    ) : this(uuid, avatarUrl, username, displayName, email, registrationDate, false)

    override fun toString(): String {
        return "[uuid=$uuid, avatarUrl=$avatarUrl, username=$username, displayName=$displayName, email=$email, registrationDate=$registrationDate]"
    }

    override fun isNew(): Boolean {
        return insert
    }

    override fun getId(): String? {
        return uuid.toString()
    }
}