package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_USER_ID
import com.bbutner.arbiter.api.util.lang.SESSION_HARMONY_USER_ID_EXTERNAL
import org.springframework.web.server.WebSession
import java.util.*

class AuthGenericHelper {
    fun storeUserIdInSession(session: WebSession, userId: Int) {
        session.attributes.putIfAbsent(SESSION_HARMONY_USER_ID, userId)
    }

    fun getUserIdFromSession(session: WebSession): Int? {
        return if (session.attributes[SESSION_HARMONY_USER_ID] == null) null else session.attributes[SESSION_HARMONY_USER_ID] as Int
    }

    fun storeUserIdExternalInSession(session: WebSession, userIdExternal: String) {
        session.attributes.putIfAbsent(SESSION_HARMONY_USER_ID_EXTERNAL, userIdExternal)
    }

    fun getUserIdExternalFromSession(session: WebSession): String? {
        return if (session.attributes[SESSION_HARMONY_USER_ID_EXTERNAL] == null) null else session.attributes[SESSION_HARMONY_USER_ID_EXTERNAL].toString()
    }
}