package com.wiyb.server.storage.repository.user.custom.impl

import com.wiyb.server.storage.entity.auth.QAuthorization.authorization
import com.wiyb.server.storage.entity.user.QUser.user
import com.wiyb.server.storage.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.entity.user.dto.QUserProfileDto
import com.wiyb.server.storage.entity.user.dto.QUserSimpleProfileDto
import com.wiyb.server.storage.entity.user.dto.UserProfileDto
import com.wiyb.server.storage.entity.user.dto.UserSimpleProfileDto
import com.wiyb.server.storage.repository.user.custom.UserCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl :
    QuerydslRepositorySupport(User::class.java),
    UserCustomRepository {
    override fun findBySessionId(sessionId: String): User? =
        from(user)
            .select(user)
            .leftJoin(user.mutableAuthorizations, authorization)
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findUserProfileDtoById(userId: Long): UserProfileDto? =
        from(user)
            .select(QUserProfileDto(user, userProfile))
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(user.id.eq(userId))
            .fetchOne()

    override fun findUserProfileDtoBySessionId(sessionId: String): UserProfileDto? =
        from(user)
            .select(QUserProfileDto(user, userProfile))
            .leftJoin(user.mutableAuthorizations, authorization)
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findUserSimpleProfileDtoByNameKeyword(keyword: String): List<UserSimpleProfileDto> =
        from(user)
            .select(QUserSimpleProfileDto(user, userProfile))
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(userProfile.nickname.containsIgnoreCase(keyword))
            .fetch()

    override fun findWithUserProfileBySessionId(sessionId: String): User? =

        from(user)
            .select(user)
            .leftJoin(user.mutableAuthorizations, authorization)
            .leftJoin(user.userProfile, userProfile)
            .fetchJoin()
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()
}
