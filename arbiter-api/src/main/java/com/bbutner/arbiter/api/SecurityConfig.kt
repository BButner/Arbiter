package com.bbutner.arbiter.api

import com.bbutner.arbiter.api.authentication.AuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.web.server.WebExceptionHandler


@EnableWebFluxSecurity
class SecurityConfig(
        @Qualifier("globalErrorHandler")
        private val exceptionHandler: WebExceptionHandler,
        private val authenticationFailureHandler: ServerAuthenticationFailureHandler,
        private val authenticationSuccessHandler: AuthenticationSuccessHandler,
        private val authorizationRequestResolver: ServerOAuth2AuthorizationRequestResolver
) {
    @Bean
    fun securityFilterChain(
            httpSecurity: ServerHttpSecurity
    ): SecurityWebFilterChain = httpSecurity
            .authorizeExchange()
            .pathMatchers("/login", "/register").permitAll()
            .anyExchange().authenticated()
            .and()
            .exceptionHandling(::withConfiguration)
            .oauth2Login(::withConfiguration)
            .cors()
            .and()
            .build()

    fun withConfiguration(spec: ServerHttpSecurity.ExceptionHandlingSpec): Unit =
            spec.accessDeniedHandler(exceptionHandler::handle)
                    .authenticationEntryPoint(exceptionHandler::handle)
                    .run {}

    fun withConfiguration(spec: ServerHttpSecurity.OAuth2LoginSpec): Unit =
            spec.authenticationFailureHandler(authenticationFailureHandler)
                    .authenticationSuccessHandler(authenticationSuccessHandler)
                    .authorizationRequestResolver(authorizationRequestResolver)
                    .run { }
}