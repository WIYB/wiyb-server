package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Wedge
import com.wiyb.server.storage.database.repository.golf.detail.custom.WedgeCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WedgeRepository :
    JpaRepository<Wedge, Long>,
    WedgeCustomRepository
