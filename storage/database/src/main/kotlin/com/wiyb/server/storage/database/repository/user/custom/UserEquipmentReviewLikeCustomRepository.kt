package com.wiyb.server.storage.database.repository.user.custom

interface UserEquipmentReviewLikeCustomRepository {
    fun existsByForeign(
        userId: Long,
        equipmentReviewId: Long
    ): Boolean

    fun findAllLikeIdByForeign(
        userId: Long,
        equipmentReviewIds: List<Long>
    ): List<Long>

    fun findAllLikeIdByForeign(
        userId: Long,
        equipmentReviewId: Long
    ): List<Long>
}
