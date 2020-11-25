package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.exception.NotFoundException
import com.bbutner.arbiter.service.model.HarmonyUser
import com.bbutner.arbiter.service.model.HarmonyUserRepository
import org.springframework.stereotype.Service

@Service
class HarmonyUserServiceImpl(
        private val harmonyUserRepository: HarmonyUserRepository
): HarmonyUserService {
    override suspend fun getUserById(id: String): HarmonyUser {
        return when (val user = harmonyUserRepository.getById(id)) {
            null -> throw NotFoundException()
            else -> user
        }
    }

    override suspend fun getUserByIdExternal(externalId: String): HarmonyUser {
        return when (val user = harmonyUserRepository.getByIdExternal(externalId)) {
            null -> throw NotFoundException()
            else -> user
        }
    }
}