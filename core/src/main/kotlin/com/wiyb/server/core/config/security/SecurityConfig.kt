package com.wiyb.server.core.config.security

import com.wiyb.server.core.filter.TokenAuthenticationFilter
import com.wiyb.server.core.filter.TokenExceptionFilter
import com.wiyb.server.core.handler.auth.CustomAccessDeniedHandler
import com.wiyb.server.core.handler.auth.CustomAuthenticationFailureHandler
import com.wiyb.server.core.handler.auth.CustomAuthenticationSuccessHandler
import com.wiyb.server.core.handler.auth.CustomLogoutSuccessHandler
import com.wiyb.server.core.service.CustomOAuth2UserService
import com.wiyb.server.core.service.CustomOidcUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfig(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customOidcUserService: CustomOidcUserService,
    private val tokenAuthenticationFilter: TokenAuthenticationFilter,
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
    @Value("\${spring.config.origin.whitelist}")
    private val whitelist: List<String>,
    @Value("\${spring.config.origin.client}")
    private val clientOrigin: String
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

        @Bean
        fun roleHierarchy(): RoleHierarchy =
            RoleHierarchyImpl
                .withDefaultRolePrefix()
                .role("ADMIN")
                .implies("USER")
                .role("USER")
                .implies("GUEST")
                .build()

        @Bean
        fun methodSecurityExpressionHandler(roleHierarchy: RoleHierarchy): MethodSecurityExpressionHandler {
            val expressionHandler = DefaultMethodSecurityExpressionHandler()
            expressionHandler.setRoleHierarchy(roleHierarchy)
            return expressionHandler
        }
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
                .anyRequest()
                .permitAll()
//                .requestMatchers(*WHITELIST_PATH)
//                .permitAll()
//                .requestMatchers(CorsUtils::isPreFlightRequest)
//                .permitAll()
//                .requestMatchers(HttpMethod.POST, "/user")
//                .hasRole(Role.GUEST.name)
//                .anyRequest()
//                .hasAnyRole(Role.USER.name, Role.ADMIN.name)
        }

        http
            .authenticationProvider(
                CustomAuthenticationProvider(
                    DefaultAuthorizationCodeTokenResponseClient(),
                    customOAuth2UserService,
                    customOidcUserService
                )
            ).authenticationProvider(
                CustomAuthenticationProvider(
                    DefaultAuthorizationCodeTokenResponseClient(),
                    customOAuth2UserService,
                    customOidcUserService
                )
            )

        http
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(TokenExceptionFilter(), tokenAuthenticationFilter::class.java)

        http.oauth2Login {
            it
                .authorizationEndpoint { endpoint ->
                    endpoint.authorizationRequestResolver(
                        CustomAuthorizationRequestResolver(
                            clientRegistrationRepository,
                            "/oauth2/authorization"
                        )
                    )
                }.userInfoEndpoint { endpoint ->
                    endpoint
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)
                }.successHandler(customAuthenticationSuccessHandler)
                .failureHandler(CustomAuthenticationFailureHandler(clientOrigin))
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

        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.allowedOrigins = whitelist
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)

        return source
    }
}
