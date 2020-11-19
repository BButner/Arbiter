package com.bbutner.arbiter.api

import com.bbutner.arbiter.api.authentication.AuthenticationSuccessHandler
import com.bbutner.arbiter.api.authentication.LogoutSuccessHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.server.WebExceptionHandler
import java.util.List


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
        @Qualifier("globalErrorHandler")
        private val exceptionHandler: WebExceptionHandler,
        private val authenticationFailureHandler: ServerAuthenticationFailureHandler,
        private val authenticationSuccessHandler: AuthenticationSuccessHandler,
        private val authorizationRequestResolver: ServerOAuth2AuthorizationRequestResolver,
        private val logoutSuccessHandler: LogoutSuccessHandler
): WebFluxConfigurer {
    fun corsConfigurationSource(): CorsConfigurationSource {
        val cors = CorsConfiguration()
        cors.allowedOrigins = List.of("*")
        cors.allowedMethods = List.of("*")
        cors.allowedHeaders = List.of("*")
        cors.allowCredentials = true
        cors.maxAge = 3600L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", cors)
        return source
    }

    @Bean
    fun securityFilterChain(
            httpSecurity: ServerHttpSecurity
    ): SecurityWebFilterChain = httpSecurity
            .cors(::withConfiguration)
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/login", "/register", "/logout").permitAll()
            .anyExchange().authenticated()
            .and()
            .exceptionHandling(::withConfiguration)
            .oauth2Login(::withConfiguration)
            .logout(::withConfiguration)
            .build()

    fun withConfiguration(spec: ServerHttpSecurity.ExceptionHandlingSpec): Unit =
            spec.accessDeniedHandler(exceptionHandler::handle)
                    .authenticationEntryPoint(exceptionHandler::handle)
                    .run {}
//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource? {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = Arrays.asList("http://localhost:3000", "http://10.0.0.97:3000")
//        configuration.allowedMethods = Arrays.asList("GET", "POST")
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }

    fun withConfiguration(spec: ServerHttpSecurity.OAuth2LoginSpec): Unit =
            spec.authenticationFailureHandler(authenticationFailureHandler)
                    .authenticationSuccessHandler(authenticationSuccessHandler)
                    .authorizationRequestResolver(authorizationRequestResolver)
                    .run { }

    fun withConfiguration(spec: ServerHttpSecurity.LogoutSpec): Unit =
            spec.logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .run { }

    fun withConfiguration(spec: ServerHttpSecurity.CorsSpec): Unit =
            spec.configurationSource(corsConfigurationSource())
                    .run { }
}