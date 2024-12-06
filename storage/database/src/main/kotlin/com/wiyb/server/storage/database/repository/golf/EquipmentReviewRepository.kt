package com.wiyb.server.storage.database.repository.golf

import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentReviewCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipmentReviewRepository :
    JpaRepository<EquipmentReview, Long>,
    EquipmentReviewCustomRepository {
    fun existsByEquipmentIdAndUserId(
        equipmentId: Long,
        userId: Long
    ): Boolean

    fun findFirstById(id: Long): EquipmentReview?
}
