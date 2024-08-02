package com.wiyb.server.storage.repository.user

import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.repository.user.custom.UserCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository :
    JpaRepository<User, Long>,
    UserCustomRepository
