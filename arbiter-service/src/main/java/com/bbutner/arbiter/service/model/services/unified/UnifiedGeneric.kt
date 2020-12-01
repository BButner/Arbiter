package com.bbutner.arbiter.service.model.services.unified

class UnifiedArtist(
        // The name of the Artist
        val name: String,
        // The link to the Artist
        val uri: String,
        // The ID of the Artist
        val id: String
) {
    override fun toString(): String {
        return "[name=$name, uri=$uri, id=$id]"
    }
}

class UnifiedAlbum(
        // Artists for the Album
        val artists: Array<UnifiedArtist>,
        // URI for the Album
        val uri: String,
        // ID of the Album
        val id: String,
        // Name of the Album
        val name: String,
        // Href to the Image of the Album
        val imageHref: String
) {
    override fun toString(): String {
        return "[artists=$artists, uri=$uri, id=$id, name=$name, imageHref=$imageHref]"
    }
}

class UnifiedSong(
        // The Album the Song appears on
        val album: UnifiedAlbum,
        // The Artists for the Song
        val artists: Array<UnifiedArtist>,
        // Whether the Song is Explicit or not
        val explicit: Boolean,
        // The URI of the Song
        val uri: String,
        // The ID of the Song
        val id: String,
        // The Name of the Song
        val name: String
) {
    override fun toString(): String {
        return "[album=$album, artists=$artists, explicit=$explicit, uri=$uri, id=$id, name=$name]"
    }
}