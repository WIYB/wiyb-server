package com.wiyb.server.storage.database.repository.user.custom.impl

import com.wiyb.server.storage.database.entity.user.QUserEquipmentBookmark.userEquipmentBookmark
import com.wiyb.server.storage.database.entity.user.UserEquipmentBookmark
import com.wiyb.server.storage.database.repository.user.custom.UserEquipmentBookmarkCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class UserEquipmentBookmarkCustomRepositoryImpl :
    QuerydslRepositorySupport(UserEquipmentBookmark::class.java),
    UserEquipmentBookmarkCustomRepository {
    override fun findAllBookmarkIdByForeign(
        userId: Long,
        equipmentId: Long
    ): List<Long> =
        from(userEquipmentBookmark)
            .select(userEquipmentBookmark.id)
            .where(
                userEquipmentBookmark.user.id
                    .eq(userId)
                    .and(userEquipmentBookmark.equipment.id.eq(equipmentId))
            ).fetch()
}
