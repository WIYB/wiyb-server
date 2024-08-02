package com.wiyb.server.core.provider

import com.wiyb.server.core.domain.auth.IssueTokenDto
import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.service.AuthorizationService
import com.wiyb.server.storage.entity.user.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class TokenProvider(
    private val authorizationService: AuthorizationService,
    @Value("\${jwt.key}")
    private val key: String
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(key.toByteArray())

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 2
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30
    }

    fun validateToken(token: String?): Claims =
        try {
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            throw CommonException(ErrorCode.EXPIRED_TOKEN)
        } catch (e: SecurityException) {
            throw CommonException(ErrorCode.INVALID_SIGNATURE)
        } catch (e: Exception) {
            throw CommonException(ErrorCode.INVALID_TOKEN)
        }

    fun validateRefreshState(
        accessToken: String,
        refreshToken: String
    ) {
        val accessClaims = parseClaims(accessToken)
        val refreshClaims = validateToken(refreshToken)

        if (accessClaims["sid"] != refreshClaims["sid"]) {
            throw CommonException(ErrorCode.INVALID_TOKEN)
        }
    }

    fun parseClaims(token: String): Claims =
        try {
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: SecurityException) {
            throw CommonException(ErrorCode.INVALID_SIGNATURE)
        } catch (e: Exception) {
            throw CommonException(ErrorCode.INVALID_TOKEN)
        }

    fun generateAccessToken(
        user: User,
        sessionId: String
    ): String {
        val claims = Jwts.claims()
        claims.add("sid", sessionId)
        claims.add("role", user.role)

        return generateToken(claims.build(), ACCESS_TOKEN_EXPIRE_TIME)
    }

    fun generateRefreshToken(
        user: User,
        sessionId: String
    ): String {
        authorizationService.deleteAllBySessionId(sessionId)

        val authorization = authorizationService.save(user, sessionId)
        val claims = Jwts.claims()
        claims.add("pid", authorization.id.toString())
        claims.add("sid", sessionId)

        return generateToken(claims.build(), REFRESH_TOKEN_EXPIRE_TIME)
    }

    fun generatePair(
        user: User,
        sessionId: String
    ): IssueTokenDto {
        val accessToken = generateAccessToken(user, sessionId)
        val refreshToken = generateRefreshToken(user, sessionId)

        return IssueTokenDto(accessToken, refreshToken)
    }

    private fun generateToken(
        claims: Claims,
        expireTime: Long
    ): String {
        val now = Date()
        val expiredDate = Date(now.time + expireTime)

        return Jwts
            .builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }
}
