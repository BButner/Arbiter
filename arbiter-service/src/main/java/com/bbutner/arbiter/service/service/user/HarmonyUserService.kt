package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.model.user.HarmonyUser

interface HarmonyUserService {
    suspend fun getUserByUuid(uuid: String): HarmonyUser
}