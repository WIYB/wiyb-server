package com.wiyb.server.storage.database.repository.user.custom

interface UserEquipmentBookmarkCustomRepository {
    fun findAllBookmarkIdByForeign(
        userId: Long,
        equipmentId: Long
    ): List<Long>
}
