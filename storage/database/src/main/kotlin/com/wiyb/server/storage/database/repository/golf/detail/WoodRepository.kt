package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.repository.golf.detail.custom.WoodCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WoodRepository :
    JpaRepository<Wood, Long>,
    WoodCustomRepository
