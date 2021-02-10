package com.bbutner.arbiter.service.model.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HarmonyUserRepository: CoroutineCrudRepository<HarmonyUser, String> {
    suspend fun getByUuid(uuid: String): HarmonyUser?

    suspend fun getByEmail(email: String): HarmonyUser?

    suspend fun getByUsername(username: String): HarmonyUser?
}