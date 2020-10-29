package com.bbutner.arbiter.service.service

import com.bbutner.arbiter.service.model.HarmonyUser

interface HarmonyUserService {
    suspend fun getUserById(id: String): HarmonyUser
}