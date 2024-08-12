package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.repository.golf.detail.custom.IronCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IronRepository :
    JpaRepository<Iron, Long>,
    IronCustomRepository
