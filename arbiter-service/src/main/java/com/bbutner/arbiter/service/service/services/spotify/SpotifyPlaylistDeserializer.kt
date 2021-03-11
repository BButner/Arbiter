package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.*
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.IntNode

class SpotifyPlaylistDeserializer : StdDeserializer<SpotifyPlaylistFull>(SpotifyPlaylistFull::class.java) {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): SpotifyPlaylistFull {
        val node: JsonNode = parser.codec.readTree(parser)

        return deserializePlaylistFull(node)
    }

    private fun deserializePlaylistFull(node: JsonNode): SpotifyPlaylistFull {
        return SpotifyPlaylistFull(
            node.get("collaborative").asBoolean(),
            if (node.get("description") != null) node.get("description").asText() else null,
            node.get("href").asText(),
            node.get("id").asText(),
            deserializeSpotifyImages(node.get("images")),
            node.get("name").asText(),
            deserializeOwner(node.get("owner")),
            if (node.get("public") != null) node.get("public").asBoolean() else null,
            node.get("snapshot_id").asText(),
            deserializeSpotifySongPagination(node.get("tracks")),
            node.get("type").asText(),
            node.get("uri").asText()
        )
    }

    private fun deserializeOwner(node: JsonNode): SpotifyUser {
        return SpotifyUser(
            node.get("display_name").asText(),
            if (node.get("followers") != null) deserializeFollower(node.get("followers")) else null,
            node.get("href").asText(),
            node.get("id").asText(),
            if (node.get("images") != null) deserializeSpotifyImages(node.get("images")) else null,
            node.get("type").asText(),
            node.get("uri").asText()
        )
    }

    private fun deserializeSpotifySongPagination(node: JsonNode): SpotifySongPagination {
        val songDeserializerHelper = SpotifySongDeserializerHelper(node.get("items"))

        return SpotifySongPagination(
            node.get("href").asText(),
            songDeserializerHelper.deserializeSpotifySongWrapper(),
            (node.get("limit") as IntNode).asInt(),
            if (node.get("next") != null && !node.get("next").isNull) node.get("next").asText() else null,
            (node.get("offset") as IntNode).asInt(),
            if (node.get("previous") != null && !node.get("previous").isNull) node.get("previous").asText() else null,
            (node.get("total") as IntNode).asInt()
        )
    }
}