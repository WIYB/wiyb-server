package com.wiyb.server.storage.database.repository.user

import com.wiyb.server.storage.database.entity.user.UserProfile
import com.wiyb.server.storage.database.repository.user.custom.UserProfileCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository :
    JpaRepository<UserProfile, Long>,
    UserProfileCustomRepository
