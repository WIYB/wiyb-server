package com.wiyb.server.core.service

import com.wiyb.server.core.exception.ErrorCode
import com.wiyb.server.core.exception.TokenException
import com.wiyb.server.storage.entity.Authorization
import com.wiyb.server.storage.repository.AuthorizationRepository
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val authorizationRepository: AuthorizationRepository
) {
    fun findTokenOrThrow(userId: Long): Authorization =
        authorizationRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
            ?: throw TokenException(ErrorCode.ILLEGAL_REGISTRATION_ID) // todo: 적절한 에러코드로 변경
}
