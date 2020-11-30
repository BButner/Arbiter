package com.bbutner.arbiter.api.exception.spotify

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "The Spotify authorization process has failed, or has not been initiated")
class SpotifyNotAuthenticatedException: RuntimeException() {}