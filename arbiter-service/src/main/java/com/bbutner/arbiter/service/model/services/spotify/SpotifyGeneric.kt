package com.bbutner.arbiter.service.model.services.spotify

import com.fasterxml.jackson.annotation.JsonProperty

class SpotifyImage(
        val height: Int?,
        val width: Int?,
        val url: String
) {
    override fun toString(): String {
        return String.format("[height=%s, width=%s, url=%s]",
            height, width, url)
    }
}

open class SpotifyGenericHrefTotal(
        val href: String?,
        val total: Int
) {
    override fun toString(): String {
        return String.format("[href=%s, total=%s]",
            href, total)
    }
}

class SpotifyFollower(href: String?, total: Int) : SpotifyGenericHrefTotal(href, total)

class SpotifyTrack(href: String?, total: Int) : SpotifyGenericHrefTotal(href, total)

class SpotifyUser(
        @JsonProperty("display_name")
        val displayName: String,
        val followers: SpotifyFollower?,
        val href: String,
        val id: String,
        val images: List<SpotifyImage> = emptyList(),
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return String.format("[displayName=%s, followers=%s, href=%s, id=%s, images=%s, type=%s, uri=%s]",
                displayName, followers, href, id, images, type, uri)
    }
}