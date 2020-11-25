package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.api.util.lang.*
import org.springframework.web.server.WebSession
import java.util.*

class SessionHelper {
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

    fun storeSpotifyAccessTokenInSession(session: WebSession, accessToken: String) {
        session.attributes[SESSION_SPOTIFY_ACCESS_TOKEN] = accessToken
    }

    fun getSpotifyAccessTokenFromSession(session: WebSession): String? {
        return session.attributes[SESSION_SPOTIFY_ACCESS_TOKEN] as String?
    }

    fun storeSpotifyUserIdInSession(session: WebSession, userId: String) {
        session.attributes[SESSION_SPOTIFY_USER_ID] = userId
    }

    fun getSpotifyUserIdFromSession(session: WebSession): String? {
        return session.attributes[SESSION_SPOTIFY_USER_ID] as String?
    }
}