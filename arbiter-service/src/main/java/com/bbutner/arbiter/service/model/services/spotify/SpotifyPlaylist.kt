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
        return "[collaborative=$collaborative, description=$description, href=$href, id=$id, images=$images, name=$name, owner=$owner, public=$public, snapshotId=$snapshotId, tracks=$tracks, type=$type, uri=$uri]"
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
        return "[href=$href, items=$items, limit=$limit, next=$next, offset=$offset, previous=$previous, total=$total]"
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
        return "[collaborative=$collaborative, description=$description, href=$href, id=$id, images=$images, name=$name, owner=$owner, public=$public, snapshotId=$snapshotId, tracks=$tracks, type=$type, uri=$uri]"
    }
}