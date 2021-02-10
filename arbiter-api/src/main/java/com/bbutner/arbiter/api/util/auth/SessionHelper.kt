package com.bbutner.arbiter.api.util.auth

import com.bbutner.arbiter.api.util.lang.*
import org.springframework.web.server.WebSession
import java.util.*

class SessionHelper {
    fun storeUserUuidInSession(session: WebSession, userUuid: UUID) {
        session.attributes.putIfAbsent(SESSION_HARMONY_USER_UUID, userUuid)
    }

    fun getUserUuidFromSession(session: WebSession): UUID? {
        return if (session.attributes[SESSION_HARMONY_USER_UUID] == null) null else UUID.fromString(session.attributes[SESSION_HARMONY_USER_UUID] as String)
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