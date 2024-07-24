package com.wiyb.server.core.config.security

import com.wiyb.server.core.exception.ErrorCode
import com.wiyb.server.core.exception.TokenException
import com.wiyb.server.core.service.TokenService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.Date
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Component
class TokenProvider(
    private val tokenService: TokenService,
    @Value("\${jwt.key}")
    private val key: String
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(key.toByteArray())

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_TIME = 120 * 60 * 1000L
        private const val REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L
        private const val KEY_ROLE = "role"
    }

    fun getAuthentication(token: String): Authentication {
        TODO()
    }

    fun validateToken(token: String): Boolean {
        if (!StringUtils.hasText(token)) return false

        val claims: Claims =
            try {
                Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .payload
            } catch (e: ExpiredJwtException) {
                e.claims
            } catch (e: MalformedJwtException) {
                throw TokenException(ErrorCode.INVALID_TOKEN)
            } catch (e: SecurityException) {
                throw TokenException(ErrorCode.INVALID_JWT_SIGNATURE)
            }

        return claims.expiration.after(Date())
    }

    fun generateAccessToken(authentication: Authentication): String {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME)
        TODO(
            """
            1. userId
            2. sessionId -> 디바이스 별 토큰 관리하기 위함
            """.trimIndent()
        )
    }

    fun generateRefreshToken(authentication: Authentication): String {
        return generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME)
        TODO("DB 저장 로직")
        TODO(
            """
            sessionId -> entity 컬럼 추가(tsid)
            """.trimIndent()
        )
    }

    private fun generateToken(
        authentication: Authentication,
        expireTime: Long
    ): String {
        val now = Date()
        val expiredDate = Date(now.time + expireTime)
        val authorities =
            authentication.authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining())

        return Jwts
            .builder()
            .subject(authentication.name)
            .claim("KEY_ROLE", authorities)
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith(secretKey, Jwts.SIG.HS512)
            .compact()
    }
}
