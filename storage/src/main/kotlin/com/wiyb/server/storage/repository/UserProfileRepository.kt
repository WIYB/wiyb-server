package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.user.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : JpaRepository<UserProfile, Long>
