package com.wiyb.server.storage.database.repository.user

import com.wiyb.server.storage.database.entity.user.UserEquipmentReviewLike
import com.wiyb.server.storage.database.repository.user.custom.UserEquipmentReviewLikeCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEquipmentReviewLikeRepository :
    JpaRepository<UserEquipmentReviewLike, Long>,
    UserEquipmentReviewLikeCustomRepository
