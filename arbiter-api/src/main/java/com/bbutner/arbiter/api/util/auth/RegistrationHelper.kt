package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.api.util.lang.DEFAULT_REGISTRATION_IMAGE_NAME
import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_REGISTER_USERNAME
import com.bbutner.arbiter.service.model.user.HarmonyUser
import com.bbutner.arbiter.service.model.user.HarmonyUserRepository
import org.springframework.web.server.WebSession
import java.time.LocalDateTime
import java.util.*

class RegistrationHelper {
    suspend fun handleRegistration(
            harmonyUserRepository: HarmonyUserRepository,
            session: WebSession,
            email: String,
            displayName: String
    ) {
        val username: String = session.attributes[SESSION_HARMONY_REGISTER_USERNAME] as String

        if (userValidForRegistration(harmonyUserRepository, email, username)) {
            val newUser: HarmonyUser = registerUser(harmonyUserRepository, email, displayName, username)

            SessionHelper().storeUserUuidInSession(session, newUser.uuid!!)
        } else {
            // we redirect with an error?
        }
    }

    private suspend fun registerUser(harmonyUserRepository: HarmonyUserRepository, email: String, displayName: String, username: String): HarmonyUser {
        return harmonyUserRepository.save(HarmonyUser(
                UUID.randomUUID(),
                DEFAULT_REGISTRATION_IMAGE_NAME,
                username,
                displayName,
                email,
                LocalDateTime.now(),
                insert = true
        ))
    }

    suspend fun userValidForRegistration(harmonyUserRepository: HarmonyUserRepository, email: String, username: String): Boolean {
        return harmonyUserRepository.getByEmail(email) == null && harmonyUserRepository.getByUsername(username) == null
    }
}