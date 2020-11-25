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
        return String.format("[accessToken=%s, tokenType=%s, scope=%s, expiresIn=%s, refreshToken=%s]",
                accessToken, tokenType, scope, expiresIn, refreshToken)
    }
}