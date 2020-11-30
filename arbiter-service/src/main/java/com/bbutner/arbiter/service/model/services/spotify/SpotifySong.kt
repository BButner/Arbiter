package com.bbutner.arbiter.service.model.services.spotify

import com.fasterxml.jackson.annotation.JsonProperty

class SpotifyArtist(
        val href: String,
        val id: String,
        val name: String,
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return String.format("[href=%s, id=%s, name=%s, type=%s, uri=%s]",
            href, id, name, type, uri)
    }
}

class SpotifyAlbum(
        @JsonProperty("album_group")
        val albumGroup: String?,
        @JsonProperty("album_type")
        val albumType: String?,
        val artists: Array<SpotifyArtist>,
        val href: String,
        val id: String,
        val images: Array<SpotifyImage>,
        val name: String,
        @JsonProperty("release_date")
        val releaseDate: String,
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return String.format("[albumGroup=%s, albumType=%s, artists=%s, href=%s, id=%s, images=%s, name=%s, releaseDate=%s, type=%s, uri=%s",
            albumGroup, albumType, artists, href, id, images, name, releaseDate, type, uri)
    }
}

class SpotifySongPagination (
        val href: String,
        val items: Array<SpotifySongWrapper>,
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

class SpotifySongWrapper(
        @JsonProperty("added_at")
        val addedAt: String,
        val track: SpotifySong
) {
    override fun toString(): String {
        return "[addedAt=$addedAt, track=$track]"
    }
}

class SpotifySong(
        val album: SpotifyAlbum,
        val artists: Array<SpotifyArtist>,
        @JsonProperty("duration_ms")
        val durationMs: Int,
        val explicit: Boolean,
        val href: String,
        val id: String,
        val name: String,
        val popularity: Int,
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return "[album=$album, artists=$artists, durationMs=$durationMs, explicit=$explicit, href=$href, id=$id, name=$name, popularity=$popularity, type=$type, uri=$uri]"
    }
}