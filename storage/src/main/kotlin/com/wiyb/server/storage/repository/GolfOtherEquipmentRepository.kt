package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.GolfOtherEquipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GolfOtherEquipmentRepository : JpaRepository<GolfOtherEquipment, Long>
