package com.bbutner.arbiter.service.service.services.unified

import com.bbutner.arbiter.service.model.services.spotify.SpotifyAlbum
import com.bbutner.arbiter.service.model.services.spotify.SpotifyArtist
import com.bbutner.arbiter.service.model.services.spotify.SpotifyPlaylist
import com.bbutner.arbiter.service.model.services.spotify.SpotifySong
import com.bbutner.arbiter.service.model.services.unified.UnifiedAlbum
import com.bbutner.arbiter.service.model.services.unified.UnifiedArtist
import com.bbutner.arbiter.service.model.services.unified.UnifiedPlaylist
import com.bbutner.arbiter.service.model.services.unified.UnifiedSong

class TranslationSpotify() {
    fun translatePlaylistToUnified(playlist: SpotifyPlaylist): UnifiedPlaylist {
        return UnifiedPlaylist(
                playlist.name,
                playlist.description,
                playlist.uri,
                playlist.id
        )
    }

    fun translateSongsToUnified(songs: Array<SpotifySong>): Array<UnifiedSong> {
        return songs.map { UnifiedSong(
                getTranslatedAlbum(it.album),
                getTranslatedArtists(it.artists),
                it.explicit,
                it.uri,
                it.id,
                it.name
        ) }.toTypedArray()
    }

    private fun getTranslatedAlbum(album: SpotifyAlbum): UnifiedAlbum {
        return UnifiedAlbum(
                getTranslatedArtists(album.artists),
                album.uri,
                album.id,
                album.name,
                album.images[0].url
        )
    }

    private fun getTranslatedArtists(artists: Array<SpotifyArtist>): Array<UnifiedArtist> {
        return artists.map { UnifiedArtist(
                it.name,
                it.uri,
                it.id
        ) }.toTypedArray()
    }
}