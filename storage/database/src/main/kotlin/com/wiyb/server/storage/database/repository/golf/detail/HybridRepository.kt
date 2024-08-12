package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Hybrid
import com.wiyb.server.storage.database.repository.golf.detail.custom.HybridCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HybridRepository :
    JpaRepository<Hybrid, Long>,
    HybridCustomRepository
