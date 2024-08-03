package com.wiyb.server.storage.database.repository.golf

import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipmentRepository :
    JpaRepository<Equipment, Long>,
    EquipmentCustomRepository
