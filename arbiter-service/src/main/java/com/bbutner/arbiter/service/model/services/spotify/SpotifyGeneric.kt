package com.bbutner.arbiter.service.model.services.spotify

import com.fasterxml.jackson.annotation.JsonProperty

class SpotifyImage(
        val height: Int?,
        val width: Int?,
        val url: String
) {
    override fun toString(): String {
        return "[height=$height, width=$width, url=$url]"
    }
}

open class SpotifyGenericHrefTotal(
        val href: String?,
        val total: Int
) {
    override fun toString(): String {
        return "[href=$href, total=$total]"
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
        val images: Array<SpotifyImage>?,
        val type: String,
        val uri: String
) {
    override fun toString(): String {
        return "[displayName=$id, followers=$id, href=$id, id=$id, images=$uri, type=$uri, uri=$uri]"
    }
}