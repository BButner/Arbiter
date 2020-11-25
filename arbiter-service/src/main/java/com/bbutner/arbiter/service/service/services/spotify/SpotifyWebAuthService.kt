package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.SpotifyAccessTokenResponse
import com.bbutner.arbiter.service.model.services.spotify.SpotifyUser
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.Base64

@Service
@Component
class SpotifyWebAuthService() {
    @Value("\${spring.security.oauth2.client.registration.spotify.client-id}")
    lateinit var clientId: String
    @Value("\${spring.security.oauth2.client.registration.spotify.client-secret}")
    lateinit var clientSecret: String

    suspend fun getAccessToken(authToken: String): SpotifyAccessTokenResponse {
        val client: WebClient = WebClient.create("https://accounts.spotify.com/api/token")
        val map: MultiValueMap<String, String> = LinkedMultiValueMap()

        map.add("grant_type", "authorization_code")
        map.add("code", authToken)
        map.add("redirect_uri", "http://10.0.0.97:3000/spotify")

        val resp = client.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Basic ${Base64.getEncoder().encodeToString("${clientId}:${clientSecret}".toByteArray())}")
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .awaitBody<SpotifyAccessTokenResponse>()

        println(resp)

        return resp
    }

    suspend fun getUserId(accessToken: String): SpotifyUser {
        val client: WebClient = WebClient.create("https://api.spotify.com/v1/me")

        val resp = client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody<SpotifyUser>()

        println(resp)

        return resp
    }
}