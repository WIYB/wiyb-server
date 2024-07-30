package com.wiyb.server.storage.repository.custom.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.wiyb.server.storage.entity.QAuthorization
import com.wiyb.server.storage.entity.QUser
import com.wiyb.server.storage.entity.QUserProfile
import com.wiyb.server.storage.entity.User
import com.wiyb.server.storage.repository.custom.UserCustomRepository
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
