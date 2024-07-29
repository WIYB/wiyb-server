package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.auth.IssueTokenDto
import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.provider.TokenProvider
import com.wiyb.server.core.service.AuthorizationService
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class AuthFacade(
    private val authorizationService: AuthorizationService,
    private val tokenProvider: TokenProvider
) {
    @Transactional
    fun refreshToken(): IssueTokenDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val authorization = authorizationService.findLatestBySessionIdWithUser(sessionId)

        if (authorization == null || Optional.ofNullable(authorization.user).isPresent.not()) {
            throw CommonException(ErrorCode.TOKEN_NOT_REFRESHABLE)
        }

        val accessToken = tokenProvider.generateAccessToken(authorization.user, sessionId)
        val refreshToken = tokenProvider.generateRefreshToken(authorization.user, sessionId)

        return IssueTokenDto(accessToken, refreshToken)
    }
}
