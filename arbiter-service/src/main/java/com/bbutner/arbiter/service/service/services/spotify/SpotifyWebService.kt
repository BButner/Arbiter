package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.SpotifyPlaylist
import com.bbutner.arbiter.service.model.services.spotify.SpotifyPlaylistPagination
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class SpotifyWebService {
    suspend fun getUserPlaylists(accessToken: String): Array<SpotifyPlaylist> {
        val client: WebClient = WebClient.create("https://api.spotify.com/v1/me/playlists")

        val resp = client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody<SpotifyPlaylistPagination>()

        println(resp)

        return resp.items
    }
}