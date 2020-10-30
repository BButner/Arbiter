package com.bbutner.arbiter.service.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserRepository: CoroutineCrudRepository<HarmonyUser, String> {
    suspend fun getById(id: String): HarmonyUser?

    suspend fun getByEmail(email: String): HarmonyUser?

    suspend fun getByUsername(username: String): HarmonyUser?

    suspend fun getIdByEmail(email: String): Int
}