package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.repository.custom.UserCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository :
    JpaRepository<User, Long>,
    UserCustomRepository
