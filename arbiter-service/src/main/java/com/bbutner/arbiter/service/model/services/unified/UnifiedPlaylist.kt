package com.bbutner.arbiter.service.model.services.unified

class UnifiedPlaylist(
        // The Name of the Playlist
        val name: String,
        // The Description of the Playlist
        val description: String?,
        // The URI of the Playlist
        val uri: String,
        // The ID of the Playlist
        val id: String,
        // The Count of Songs on the Playlist
        val songCount: Int,
        // The Songs in the Playlist
        val songs: Array<UnifiedSong>
) {
    override fun toString(): String {
        return "[name=$name, uri=$uri, id=$id, songCount=$songCount, songs=$songs]"
    }
}