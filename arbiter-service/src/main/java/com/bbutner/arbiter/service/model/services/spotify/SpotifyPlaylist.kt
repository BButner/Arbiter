package com.bbutner.arbiter.service.model.services.spotify

import com.fasterxml.jackson.annotation.JsonProperty

class SpotifyPlaylist(
        val collaborative: Boolean,
        val description: String?,
        val href: String,
        val id: String,
        val images: Array<SpotifyImage>,
        val name: String,
        val owner: SpotifyUser,
        val public: Boolean?,
        @JsonProperty("snapshot_id")
        val snapshotId: String,
        val tracks: SpotifyTrack,
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return String.format("[collaborative=%s, description=%s, href=%s, id=%s, images=%s, name=%s, owner=%s, public=%s, snapshotId=%s, tracks=%s, type=%s, uri=%s]",
            collaborative, description, href, id, images, name, owner, public, snapshotId, tracks, type, uri)
    }
}

class SpotifyPlaylistPagination (
        val href: String,
        val items: Array<SpotifyPlaylist>,
        val limit: Int,
        val next: String?,
        val offset: Int,
        val previous: String?,
        val total: Int
) {
    override fun toString(): String {
        return String.format("[href=%s, items=%s, limit=%s, next=%s, offset=%s, previous=%s, total=%s]",
            href, items, limit, next, offset, previous, total)
    }
}

class SpotifyPlaylistFull(
        val collaborative: Boolean,
        val description: String?,
        val href: String,
        val id: String,
        val images: Array<SpotifyImage>,
        val name: String,
        val owner: SpotifyUser,
        val public: Boolean?,
        @JsonProperty("snapshot_id")
        val snapshotId: String,
        val tracks: SpotifySongPagination,
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return String.format("[collaborative=%s, description=%s, href=%s, id=%s, images=%s, name=%s, owner=%s, public=%s, snapshotId=%s, tracks=%s, type=%s, uri=%s]",
                collaborative, description, href, id, images, name, owner, public, snapshotId, tracks, type, uri)
    }
}