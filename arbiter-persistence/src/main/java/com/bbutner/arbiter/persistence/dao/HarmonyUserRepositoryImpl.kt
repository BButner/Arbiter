package com.bbutner.arbiter.persistence.dao

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import com.bbutner.arbiter.service.model.user.HarmonyUser
import com.bbutner.arbiter.service.model.user.HarmonyUserRepository

@Repository
interface HarmonyUserRepositoryImpl: HarmonyUserRepository, CoroutineCrudRepository<HarmonyUser, String> {
    @Query("select * from user where uuid = ?")
    override suspend fun getByUuid(uuid: String): HarmonyUser

    @Query("select * from user where email = ?")
    override suspend fun getByEmail(email: String): HarmonyUser?

    @Query("select * from user where username = ?")
    override suspend fun getByUsername(username: String): HarmonyUser?
}