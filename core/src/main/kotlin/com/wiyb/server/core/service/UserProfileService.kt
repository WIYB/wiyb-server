package com.wiyb.server.core.service

import com.wiyb.server.storage.entity.user.UserProfile
import com.wiyb.server.storage.repository.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository
) {
    fun save(userProfile: UserProfile): UserProfile = userProfileRepository.save(userProfile)
}
