package com.bbutner.arbiter.service.service.user

import com.bbutner.arbiter.service.exception.NotFoundException
import com.bbutner.arbiter.service.model.user.HarmonyUser
import com.bbutner.arbiter.service.model.user.HarmonyUserRepository
import org.springframework.stereotype.Service

@Service
class HarmonyUserServiceImpl(
        private val harmonyUserRepository: HarmonyUserRepository
): HarmonyUserService {
    override suspend fun getUserByUuid(uuid: String): HarmonyUser {
        return when (val user = harmonyUserRepository.getByUuid(uuid)) {
            null -> throw NotFoundException()
            else -> user
        }
    }
}