package com.bbutner.arbiter.service.model.services.unified

class UnifiedPlaylist(
        // The Name of the Playlist
        val name: String,
        // The Description of the Playlist
        val description: String?,
        // The URI of the Playlist
        val uri: String,
        // The ID of the Playlist
        val id: String
) {
    override fun toString(): String {
        return "[name=$name, description=$description, uri=$uri, id=$id]"
    }
}