package com.wiyb.server.storage.database.repository.user

import com.wiyb.server.storage.database.entity.user.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : JpaRepository<UserProfile, Long>
