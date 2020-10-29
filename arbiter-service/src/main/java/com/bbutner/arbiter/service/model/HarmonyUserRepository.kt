package com.bbutner.arbiter.service.model

import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserRepository {
    suspend fun getById(id: String): HarmonyUser?

    suspend fun getByEmail(email: String): HarmonyUser?
}