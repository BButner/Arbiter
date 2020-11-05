package com.bbutner.arbiter.service.service

import com.bbutner.arbiter.service.model.HarmonyUser
import java.util.*

interface HarmonyUserService {
    suspend fun getUserById(id: String): HarmonyUser
    suspend fun getUserByIdExternal(externalId: String): HarmonyUser
}