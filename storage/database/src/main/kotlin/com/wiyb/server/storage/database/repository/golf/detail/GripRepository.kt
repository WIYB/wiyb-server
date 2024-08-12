package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Grip
import com.wiyb.server.storage.database.repository.golf.detail.custom.GripCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GripRepository :
    JpaRepository<Grip, Long>,
    GripCustomRepository
