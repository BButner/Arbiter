package com.bbutner.arbiter.service.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HarmonyUserRepository: CoroutineCrudRepository<HarmonyUser, String> {
    suspend fun getById(id: String): HarmonyUser?

    suspend fun getByEmail(email: String): HarmonyUser?

    suspend fun getByUsername(username: String): HarmonyUser?

    suspend fun getByIdExternal(idExternal: String): HarmonyUser?
}