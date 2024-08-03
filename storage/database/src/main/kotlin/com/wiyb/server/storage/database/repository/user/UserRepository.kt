package com.wiyb.server.storage.database.repository.user

import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.repository.user.custom.UserCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository :
    JpaRepository<User, Long>,
    UserCustomRepository
