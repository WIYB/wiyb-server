package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Driver
import com.wiyb.server.storage.database.repository.golf.detail.custom.DriverCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DriverRepository :
    JpaRepository<Driver, Long>,
    DriverCustomRepository
