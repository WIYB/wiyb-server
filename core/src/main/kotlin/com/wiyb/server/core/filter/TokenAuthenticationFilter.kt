package com.wiyb.server.core.filter

import com.wiyb.server.core.config.security.SecurityConfig
import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.provider.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Arrays
import java.util.Collections

@Component
class TokenAuthenticationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        when {
            (isPermittedURI(request.requestURI)) -> {
                SecurityContextHolder.getContext().authentication = null
            }

            (isRefreshURI(request)) -> {
                val accessToken = resolveToken(request, "access")
                val refreshToken = resolveToken(request, "refresh")

                if (accessToken == null || refreshToken == null) {
                    throw CommonException(ErrorCode.INVALID_TOKEN)
                }

                tokenProvider.validateRefreshState(accessToken, refreshToken)
                SecurityContextHolder.getContext().authentication = getAuthentication(accessToken)
            }

            else -> {
                val accessToken = resolveToken(request, "access") ?: throw CommonException(ErrorCode.INVALID_TOKEN)

                tokenProvider.validateToken(accessToken)
                SecurityContextHolder.getContext().authentication = getAuthentication(accessToken)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun isPermittedURI(uri: String): Boolean =
        Arrays
            .stream(SecurityConfig.WHITELIST_PATH)
            .anyMatch { permitted ->
                val replace: String = permitted.replace("*", "")
                uri.contains(replace) || replace.contains(uri)
            }

    private fun isRefreshURI(request: HttpServletRequest): Boolean =
        request.method == RequestMethod.PATCH.name && request.requestURI == "/auth/token"

    private fun resolveToken(
        request: HttpServletRequest,
        target: String
    ): String? =
        Arrays
            .stream(request.cookies ?: arrayOf())
            .filter { it.name.equals(target) }
            .findAny()
            .map(Cookie::getValue)
            .orElse(null)

    private fun getAuthentication(token: String): Authentication {
        val claims = tokenProvider.parseClaims(token)
        val authorities: Set<SimpleGrantedAuthority> = Collections.singleton(SimpleGrantedAuthority("ROLE_" + claims["role"] as String))
        return UsernamePasswordAuthenticationToken(User(claims["sid"] as String, "", authorities), token, authorities)
    }
}
