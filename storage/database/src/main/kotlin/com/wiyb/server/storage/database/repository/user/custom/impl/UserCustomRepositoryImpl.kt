package com.wiyb.server.storage.database.repository.user.custom.impl

import com.wiyb.server.storage.database.entity.auth.QAuthorization.authorization
import com.wiyb.server.storage.database.entity.user.QUser.user
import com.wiyb.server.storage.database.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.dto.QUserProfileDto
import com.wiyb.server.storage.database.entity.user.dto.QUserSimpleProfileDto
import com.wiyb.server.storage.database.entity.user.dto.UserProfileDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import com.wiyb.server.storage.database.repository.user.custom.UserCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl :
    QuerydslRepositorySupport(User::class.java),
    UserCustomRepository {
    override fun findIdBySessionId(sessionId: String): Long? =
        from(user)
            .select(user.id)
            .leftJoin(user.mutableAuthorizations, authorization)
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findBySessionId(sessionId: String): User? =
        from(user)
            .select(user)
            .leftJoin(user.mutableAuthorizations, authorization)
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findUserProfileDtoById(userId: Long): UserProfileDto? =
        from(user)
            .select(
                QUserProfileDto(
                    user.id.stringValue(),
                    userProfile.nickname,
                    userProfile.gender,
                    userProfile.birth,
                    userProfile.handy,
                    userProfile.height,
                    userProfile.weight,
                    userProfile.imageUrl,
                    userProfile.createdAt
                )
            ).leftJoin(user.userProfile, userProfile)
            .where(user.id.eq(userId))
            .fetchOne()

    override fun findUserProfileDtoBySessionId(sessionId: String): UserProfileDto? =
        from(user)
            .select(
                QUserProfileDto(
                    user.id.stringValue(),
                    userProfile.nickname,
                    userProfile.gender,
                    userProfile.birth,
                    userProfile.handy,
                    userProfile.height,
                    userProfile.weight,
                    userProfile.imageUrl,
                    userProfile.createdAt
                )
            ).leftJoin(user.mutableAuthorizations, authorization)
            .leftJoin(user.userProfile, userProfile)
            .where(authorization.sessionId.eq(sessionId))
            .fetchOne()

    override fun findUserSimpleProfileDtoByNameKeyword(keyword: String): List<UserSimpleProfileDto> =
        from(user)
            .select(
                QUserSimpleProfileDto(
                    user.id.stringValue(),
                    userProfile.nickname,
                    userProfile.handy,
                    userProfile.height,
                    userProfile.weight,
                    userProfile.imageUrl
                )
            ).leftJoin(user.userProfile, userProfile)
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
