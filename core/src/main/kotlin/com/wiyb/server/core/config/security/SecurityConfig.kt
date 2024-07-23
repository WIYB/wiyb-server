package com.wiyb.server.core.config.security

import com.wiyb.server.core.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val customOAuth2UserService: CustomOAuth2UserService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationRequestResolver =
            CustomAuthorizationRequestResolver(
                clientRegistrationRepository,
                "/v1/auth/sign"
            )

        // todo: 나중에 어드민 구현할 때 requestMatcher 추가
        http.csrf {
            it
                .disable()
        }

        http.headers {
            it
                .frameOptions { }
                .disable()
        }

        http.authorizeHttpRequests {
            it
                .requestMatchers(
                    "/h2-console/**",
                    "/v1/auth/sign",
                    "/v1/auth/sign/success",
                    "/v1/auth/sign/good",
                    "/v1/auth/sign/failure"
                ).permitAll()
                .anyRequest()
                .authenticated()
        }

        http.oauth2Login {
            it
                .authorizationEndpoint { endpoint ->
                    endpoint
                        .baseUri("/v1/auth/sign")
                        .authorizationRequestResolver(authorizationRequestResolver)
                }.redirectionEndpoint { endpoint ->
                    endpoint.baseUri("/v1/auth/sign/success")
                }.userInfoEndpoint { endpoint ->
                    endpoint.userService(customOAuth2UserService)
                }.successHandler { _, response, authentication ->
                    response.setHeader("hello", "world")
                    response.sendRedirect("/v1/auth/sign/good")
                }.failureHandler(CustomAuthenticationFailureHandler())
        }

        http.logout {
            it
                .logoutUrl("/v1/auth/sign/out")
                .logoutSuccessUrl("/")
        }

        return http.build()
    }
}
