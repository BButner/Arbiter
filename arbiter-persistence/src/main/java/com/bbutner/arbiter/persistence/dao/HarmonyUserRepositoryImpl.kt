package com.bbutner.arbiter.persistence.dao

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import com.bbutner.arbiter.service.model.HarmonyUser
import com.bbutner.arbiter.service.model.HarmonyUserRepository

@Repository
interface HarmonyUserRepositoryImpl: HarmonyUserRepository, CoroutineCrudRepository<HarmonyUser, String> {
    @Query("select * from user where id = ?")
    override suspend fun getById(id: String): HarmonyUser

    @Query("select * from user where email = ?")
    override suspend fun getByEmail(email: String): HarmonyUser?
}