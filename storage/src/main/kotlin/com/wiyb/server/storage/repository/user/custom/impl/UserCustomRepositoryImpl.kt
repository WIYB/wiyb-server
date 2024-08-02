package com.wiyb.server.storage.repository.user.custom.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.wiyb.server.storage.entity.auth.QAuthorization
import com.wiyb.server.storage.entity.user.QUser
import com.wiyb.server.storage.entity.user.QUserProfile
import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.repository.user.custom.UserCustomRepository
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : UserCustomRepository {
    override fun findBySessionId(sessionId: String): User? =
        queryFactory
            .select(QUser.user)
            .from(QUser.user)
            .leftJoin(QUser.user.mutableAuthorizations, QAuthorization.authorization)
            .where(QAuthorization.authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findWithUserProfileById(userId: Long): User? =
        queryFactory
            .select(QUser.user)
            .from(QUser.user)
            .leftJoin(QUser.user.userProfile, QUserProfile.userProfile)
            .fetchJoin()
            .where(QUser.user.id.eq(userId))
            .fetchOne()

    override fun findWithUserProfileBySessionId(sessionId: String): User? =
        queryFactory
            .select(QUser.user)
            .from(QUser.user)
            .leftJoin(QUser.user.mutableAuthorizations, QAuthorization.authorization)
            .leftJoin(QUser.user.userProfile, QUserProfile.userProfile)
            .fetchJoin()
            .where(QAuthorization.authorization.sessionId.eq(sessionId))
            .fetchOne()
}
