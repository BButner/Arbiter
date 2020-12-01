package com.bbutner.arbiter.api.controllers.service

import com.bbutner.arbiter.api.exception.spotify.SpotifyNotAuthenticatedException
import com.bbutner.arbiter.api.util.auth.SessionHelper
import com.bbutner.arbiter.service.model.services.spotify.SpotifyAccessTokenResponse
import com.bbutner.arbiter.service.model.services.spotify.SpotifySong
import com.bbutner.arbiter.service.model.services.spotify.SpotifyUser
import com.bbutner.arbiter.service.model.services.unified.UnifiedPlaylist
import com.bbutner.arbiter.service.model.services.unified.UnifiedSong
import com.bbutner.arbiter.service.service.services.spotify.SpotifyWebAuthService
import com.bbutner.arbiter.service.service.services.spotify.SpotifyWebService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.server.awaitSession
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession

class SpotifyTokenBody(
        val token: String
)

@RestController
@RequestMapping("/service/spotify")
@PreAuthorize("hasRole('USER')")
class ServiceSpotifyController(
        private val spotifyWebAuthService: SpotifyWebAuthService,
        private val spotifyWebService: SpotifyWebService
) {
    @PostMapping("/token")
    suspend fun handleTokenStorage(@AuthenticationPrincipal user: OAuth2User, @RequestBody body: SpotifyTokenBody, exchange: ServerWebExchange) {
        val session: WebSession = exchange.awaitSession()

        val accessTokenResponse: SpotifyAccessTokenResponse = spotifyWebAuthService.getAccessToken(body.token)
        val userIdResponse: SpotifyUser = spotifyWebAuthService.getUserId(accessTokenResponse.accessToken)

        SessionHelper().storeSpotifyAccessTokenInSession(session, accessTokenResponse.accessToken)
        SessionHelper().storeSpotifyUserIdInSession(session, userIdResponse.id)

        return
    }

    @GetMapping("/playlists")
    suspend fun handlePlaylistRetrieval(@AuthenticationPrincipal user: OAuth2User, exchange: ServerWebExchange): Array<UnifiedPlaylist> {
        val spotifyAccessToken: String? = SessionHelper().getSpotifyAccessTokenFromSession(exchange.awaitSession())

        if (spotifyAccessToken != null) {
            val playlistResponse: Array<UnifiedPlaylist> = spotifyWebService.getUserPlaylists(spotifyAccessToken)

            return playlistResponse
        } else {
            throw SpotifyNotAuthenticatedException()
        }
    }

    @GetMapping("/playlists/{playlistId}/songs")
    suspend fun getSongsForPlaylist(@AuthenticationPrincipal user: OAuth2User, @PathVariable playlistId: String, exchange: ServerWebExchange): Array<UnifiedSong> {
        val spotifyAccessTokenResponse: String? = SessionHelper().getSpotifyAccessTokenFromSession(exchange.awaitSession())

        if (spotifyAccessTokenResponse != null) {
//            val songsResponse: Array<UnifiedSong> = spotifyWebService.getSongsForPlaylist(spotifyAccessTokenResponse, playlistId)
            val test = spotifyWebService.getSongsByPlaylistId(spotifyAccessTokenResponse, playlistId)

            return test
        } else {
            throw SpotifyNotAuthenticatedException()
        }
    }
}