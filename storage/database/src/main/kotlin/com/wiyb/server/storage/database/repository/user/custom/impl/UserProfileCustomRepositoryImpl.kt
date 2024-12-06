package com.wiyb.server.storage.database.repository.user.custom.impl

import com.wiyb.server.storage.database.entity.auth.QAuthorization.authorization
import com.wiyb.server.storage.database.entity.user.QUser.user
import com.wiyb.server.storage.database.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.database.entity.user.UserProfile
import com.wiyb.server.storage.database.repository.user.custom.UserProfileCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserProfileCustomRepositoryImpl :
    QuerydslRepositorySupport(UserProfileCustomRepositoryImpl::class.java),
    UserProfileCustomRepository {
    override fun findBySessionId(sessionId: String): UserProfile? =
        from(userProfile)
            .select(userProfile)
            .innerJoin(userProfile.user, user)
            .innerJoin(user.mutableAuthorizations, authorization)
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()
}
