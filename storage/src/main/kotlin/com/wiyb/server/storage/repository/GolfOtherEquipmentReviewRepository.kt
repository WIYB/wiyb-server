package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.GolfOtherEquipmentReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GolfOtherEquipmentReviewRepository : JpaRepository<GolfOtherEquipmentReview, Long>
