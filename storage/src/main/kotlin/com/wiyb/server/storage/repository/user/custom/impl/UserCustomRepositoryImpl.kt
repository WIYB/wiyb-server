package com.wiyb.server.storage.repository.user.custom.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.wiyb.server.storage.entity.auth.QAuthorization.authorization
import com.wiyb.server.storage.entity.user.QUser.user
import com.wiyb.server.storage.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.entity.user.dto.QUserProfileDto
import com.wiyb.server.storage.entity.user.dto.UserProfileDto
import com.wiyb.server.storage.repository.user.custom.UserCustomRepository
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : UserCustomRepository {
    override fun findBySessionId(sessionId: String): User? =
        queryFactory
            .select(user)
            .from(user)
            .leftJoin(user.mutableAuthorizations, authorization)
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findUserProfileDtoById(userId: Long): UserProfileDto? =
        queryFactory
            .select(QUserProfileDto(user, userProfile))
            .from(user)
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(user.id.eq(userId))
            .fetchOne()

    override fun findUserProfileDtoBySessionId(sessionId: String): UserProfileDto? =
        queryFactory
            .select(QUserProfileDto(user, userProfile))
            .from(user)
            .leftJoin(user.mutableAuthorizations, authorization)
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findWithUserProfileBySessionId(sessionId: String): User? =
        queryFactory
            .select(user)
            .from(user)
            .leftJoin(user.mutableAuthorizations, authorization)
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()
}
