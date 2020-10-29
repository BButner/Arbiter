package com.bbutner.arbiter.api.authentication

import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.stereotype.Component

@Component
class DefaultOAuth2AuthorizationRequestResolver(
        clientRegistrationRepository: ReactiveClientRegistrationRepository
) : ServerOAuth2AuthorizationRequestResolver by DefaultServerOAuth2AuthorizationRequestResolver(
        clientRegistrationRepository,
        PathPatternParserServerWebExchangeMatcher("/login${DefaultServerOAuth2AuthorizationRequestResolver.DEFAULT_AUTHORIZATION_REQUEST_PATTERN}")
)