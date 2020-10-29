package com.bbutner.arbiter.api.authentication

import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class DefaultAuthenticationHandler :
        ServerAuthenticationFailureHandler by RedirectServerAuthenticationFailureHandler("/login?status=error"),
        ServerAuthenticationSuccessHandler by RedirectServerAuthenticationSuccessHandler("/login?status=success")