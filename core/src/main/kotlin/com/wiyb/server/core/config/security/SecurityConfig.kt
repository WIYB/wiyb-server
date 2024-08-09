package com.wiyb.server.core.config.security

import com.wiyb.server.core.filter.TokenAuthenticationFilter
import com.wiyb.server.core.filter.TokenExceptionFilter
import com.wiyb.server.core.handler.auth.CustomAccessDeniedHandler
import com.wiyb.server.core.handler.auth.CustomAuthenticationFailureHandler
import com.wiyb.server.core.handler.auth.CustomAuthenticationSuccessHandler
import com.wiyb.server.core.handler.auth.CustomLogoutSuccessHandler
import com.wiyb.server.core.service.CustomOAuth2UserService
import com.wiyb.server.storage.database.entity.user.constant.Role
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val tokenAuthenticationFilter: TokenAuthenticationFilter,
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler
) {
    companion object {
        val WHITELIST_PATH: Array<String> =
            arrayOf(
                "/health",
                "/favicon.ico",
                "/h2-console/**",
                "/auth/success",
                "/login/**",
                "/error",
                "/main",
                "/sign/**"
            )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .headers { it.frameOptions { f -> f.disable() }.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors { it.configurationSource(corsConfigurationSource()) }

        http.authorizeHttpRequests {
            it
                .requestMatchers(PathRequest.toH2Console())
                .permitAll()
                .requestMatchers(*WHITELIST_PATH)
                .permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/user")
                .hasRole(Role.GUEST.name)
                .anyRequest()
                .hasAnyRole(Role.USER.name, Role.ADMIN.name)
        }

        http
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(TokenExceptionFilter(), tokenAuthenticationFilter::class.java)

        http.oauth2Login {
            it
                .userInfoEndpoint { u -> u.userService(customOAuth2UserService) }
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(CustomAuthenticationFailureHandler())
        }

        http.logout {
            it
                .logoutUrl("/logout")
                .logoutSuccessHandler(CustomLogoutSuccessHandler())
                .deleteCookies("access", "refresh")
        }

        http.exceptionHandling { it.accessDeniedHandler(CustomAccessDeniedHandler()) }

        return http.build()
    }

    @Bean
    @ConditionalOnProperty(name = ["spring.h2.console.enabled"], havingValue = "true")
    fun configureH2ConsoleEnable(): WebSecurityCustomizer =
        WebSecurityCustomizer { web: WebSecurity ->
            web
                .ignoring()
                .requestMatchers(PathRequest.toH2Console())
        }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        val source = UrlBasedCorsConfigurationSource()

        config.addAllowedOrigin("http://localhost:3000")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)

        return source
    }
}
