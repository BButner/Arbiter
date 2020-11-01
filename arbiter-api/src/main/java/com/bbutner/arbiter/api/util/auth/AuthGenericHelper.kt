package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_USER_ID
import org.springframework.web.server.WebSession

class AuthGenericHelper {
    fun storeUserIdInSession(session: WebSession, userId: Int) {
        session.attributes.putIfAbsent(SESSION_HARMONY_USER_ID, userId)
    }

    fun getUserIdFromSession(session: WebSession): String? {
        return if (session.attributes[SESSION_HARMONY_USER_ID] == null) null else session.attributes[SESSION_HARMONY_USER_ID].toString()
    }
}