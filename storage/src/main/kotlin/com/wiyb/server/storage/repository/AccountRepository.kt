package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.Account
import com.wiyb.server.storage.repository.custom.AccountCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository :
    JpaRepository<Account, Long>,
    AccountCustomRepository {
    fun findByEmail(email: String): Account?
}
