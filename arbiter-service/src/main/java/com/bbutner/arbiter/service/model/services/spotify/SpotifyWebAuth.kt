package com.bbutner.arbiter.service.model.services.spotify

import com.fasterxml.jackson.annotation.JsonProperty

class SpotifyAccessTokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        val scope: String,
        @JsonProperty("expires_in")
        val expiresIn: Int,
        @JsonProperty("refresh_token")
        val refreshToken: String
) {
    override fun toString(): String {
        return "[accessToken=$accessToken, tokenType=$tokenType, scope=$scope, expiresIn=$expiresIn, refreshToken=$refreshToken]"
    }
}