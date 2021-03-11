package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.*
import com.bbutner.arbiter.service.model.services.unified.UnifiedPlaylist
import com.bbutner.arbiter.service.model.services.unified.UnifiedSong
import com.bbutner.arbiter.service.service.services.unified.TranslationSpotify
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class SpotifyWebService {
    suspend fun getUserPlaylists(accessToken: String): Array<UnifiedPlaylist> {
        val client: WebClient = WebClient.create("https://api.spotify.com/v1/me/playlists")

        val resp = client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody<SpotifyPlaylistPagination>()

        return resp.items.map { TranslationSpotify().translatePlaylistToUnified(it) }.toTypedArray()
    }

    suspend fun getSongsByPlaylistId(accessToken: String, playlistId: String): Array<UnifiedSong> {
        val playlist: SpotifyPlaylistFull = getPlaylistById(accessToken, playlistId)
        val songs: ArrayList<SpotifySong> = arrayListOf()
        var next: String? = playlist.tracks.next
        songs.addAll(playlist.tracks.items.filter { item -> !item.isLocal }.map { songWrapper -> songWrapper.track } )

        if (next != null) {
            do {
                val moreSongs: SpotifySongPagination = fetchMoreSongs(accessToken, next!!)
                songs.addAll(moreSongs.items.filter { item -> !item.isLocal }.map { spotifySongWrapper -> spotifySongWrapper.track })
                next = moreSongs.next
            } while (next != null)
        }

        return TranslationSpotify().translateSongsToUnified(songs.toTypedArray())
    }

    private suspend fun getPlaylistById(accessToken: String, playlistId: String): SpotifyPlaylistFull {
        val client: WebClient = WebClient.builder().exchangeStrategies(
            ExchangeStrategies.builder()
                    .codecs { config -> config.defaultCodecs().maxInMemorySize(16 * 1024 * 500) }
                    .build())
            .baseUrl("https://api.spotify.com/v1/playlists/$playlistId")
            .build()

        val result = client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody<SpotifyPlaylistFull>()

        return result
    }

    private suspend fun fetchMoreSongs(accessToken: String, href: String): SpotifySongPagination {
        val client: WebClient = WebClient.create(href)
        return client.get()
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .awaitBody()
    }
}