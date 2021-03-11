package com.bbutner.arbiter.service.service.services.spotify

import com.bbutner.arbiter.service.model.services.spotify.SpotifyFollower
import com.bbutner.arbiter.service.model.services.spotify.SpotifyImage
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.IntNode

fun deserializeFollower(node: JsonNode): SpotifyFollower {
    return SpotifyFollower(
        if (node.get("href") != null) node.get("href").asText() else null,
        (node.get("total") as IntNode).intValue()
    )
}

fun deserializeSpotifyImages(node: JsonNode): Array<SpotifyImage> {
    return node.map {
        SpotifyImage(
            if (it.get("height") == null || it.get("height").isNull) null else (it.get("height") as IntNode).asInt(),
            if (it.get("width") == null || it.get("height").isNull) null else (it.get("width") as IntNode).asInt(),
            it.get("url").asText()
        )
    }.toTypedArray()
}