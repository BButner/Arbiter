package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.SpotifyPlaylist
import com.bbutner.arbiter.service.model.services.spotify.SpotifyPlaylistPagination
import com.bbutner.arbiter.service.model.services.spotify.SpotifySong
import com.bbutner.arbiter.service.model.services.spotify.SpotifySongPagination
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
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

        resp.items.forEach { println(it.name) ; it.songs = getSongsForPlaylist(accessToken, it) }

        return resp.items
    }

    private suspend fun getSongsForPlaylist(accessToken: String, playlist: SpotifyPlaylist): Array<SpotifySong> {
        val songs: ArrayList<SpotifySong> = arrayListOf()
        var next: String? = null

        val client: WebClient = WebClient.builder().exchangeStrategies(
                ExchangeStrategies.builder()
                        .codecs { config -> config.defaultCodecs().maxInMemorySize(16 * 1024 * 100) }
                        .build())
                .baseUrl(playlist.tracks.href)
                .build()
        val resp = client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody<SpotifySongPagination>()

        next = resp.next

        // TODO: Add limitations on this, we don't want to return EVERY song for EVERY playlist
        // Perhaps we should instead just return the playlists, and then load the songs for each playlists ad hoc?
        songs.addAll(resp.items.map { spotifySongWrapper -> spotifySongWrapper.track })

        if (next != null) {
            do {
                val moreSongs: SpotifySongPagination = fetchMoreSongs(accessToken, next!!)
                songs.addAll(moreSongs.items.map { spotifySongWrapper -> spotifySongWrapper.track })
                next = moreSongs.next
                println("Getting MORE songs for: ${playlist.name}")
            } while (next != null)
        }

        return songs.toTypedArray()
    }

    private suspend fun fetchMoreSongs(accessToken: String, href: String): SpotifySongPagination {
        val client: WebClient = WebClient.create(href)
        return client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody()
    }
}