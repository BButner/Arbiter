package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.SpotifyAlbum
import com.bbutner.arbiter.service.model.services.spotify.SpotifyArtist
import com.bbutner.arbiter.service.model.services.spotify.SpotifySong
import com.bbutner.arbiter.service.model.services.spotify.SpotifySongWrapper
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.IntNode

class LocalFilter (
    val addedAt: String,
    val isLocal: Boolean,
    val track: JsonNode
) { }

class SpotifySongDeserializerHelper (
    private val spotifySongWrapperNode: JsonNode
) {
    fun deserializeSpotifySongWrapper(): Array<SpotifySongWrapper> {
        return spotifySongWrapperNode.map {
            LocalFilter(
                it.get("added_at").asText(),
                it.get("is_local").asBoolean(),
                it.get("track")
            )
        }
            .filter { !it.isLocal }
            .map {
                SpotifySongWrapper(
                    it.addedAt,
                    it.isLocal,
                    deserializeSpotifySong(it.track)
                )
            }.toTypedArray()
    }

    private fun deserializeSpotifySong(node: JsonNode): SpotifySong {
        return SpotifySong(
            deserializeSpotifyAlbum(node.get("album")),
            deserializeSpotifyArtists(node.get("artists")),
            (node.get("duration_ms") as IntNode).asInt(),
            node.get("explicit").asBoolean(),
            node.get("href").asText(),
            node.get("id").asText(),
            node.get("name").asText(),
            (node.get("popularity") as IntNode).asInt(),
            node.get("type").asText(),
            node.get("uri").asText()
        )
    }

    private fun deserializeSpotifyAlbum(node: JsonNode): SpotifyAlbum {
        return SpotifyAlbum(
            if (node.get("album_group") != null && !node.get("album_group").isNull) node.get("album_group").asText() else null,
            if (node.get("album_type") != null && !node.get("album_type").isNull) node.get("album_type").asText() else null,
            deserializeSpotifyArtists(node.get("artists")),
            node.get("href").asText(),
            node.get("id").asText(),
            deserializeSpotifyImages(node.get("images")),
            node.get("name").asText(),
            node.get("release_date").asText(),
            node.get("type").asText(),
            node.get("uri").asText()
        )
    }

    private fun deserializeSpotifyArtists(node: JsonNode): Array<SpotifyArtist> {
        return node.map {
            SpotifyArtist(
                it.get("href").asText(),
                it.get("id").asText(),
                it.get("name").asText(),
                it.get("type").asText(),
                it.get("uri").asText()
            )
        }.toTypedArray()
    }
}