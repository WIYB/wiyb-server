package com.wiyb.server.storage.database.repository.user

import com.wiyb.server.storage.database.entity.user.UserEquipmentBookmark
import com.wiyb.server.storage.database.repository.user.custom.UserEquipmentBookmarkCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEquipmentBookmarkRepository :
    JpaRepository<UserEquipmentBookmark, Long>,
    UserEquipmentBookmarkCustomRepository {
    fun existsByUserIdAndEquipmentId(
        userId: Long,
        equipmentId: Long
    ): Boolean
}
