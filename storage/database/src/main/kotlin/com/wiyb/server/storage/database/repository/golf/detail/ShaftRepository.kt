package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Shaft
import com.wiyb.server.storage.database.repository.golf.detail.custom.ShaftCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShaftRepository :
    JpaRepository<Shaft, Long>,
    ShaftCustomRepository
