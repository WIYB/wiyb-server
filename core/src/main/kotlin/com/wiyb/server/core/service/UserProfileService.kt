package com.wiyb.server.core.service

import com.wiyb.server.storage.database.entity.user.UserProfile
import com.wiyb.server.storage.database.repository.user.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository
) {
    fun save(userProfile: UserProfile): UserProfile = userProfileRepository.save(userProfile)
}
