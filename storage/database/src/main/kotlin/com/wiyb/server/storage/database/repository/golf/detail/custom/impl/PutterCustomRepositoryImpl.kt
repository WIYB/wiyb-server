package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.detail.Putter
import com.wiyb.server.storage.database.entity.golf.detail.QPutter.putter
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.custom.PutterCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PutterCustomRepositoryImpl :
    QuerydslRepositorySupport(Putter::class.java),
    PutterCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(putter)
            .select(
                QEquipmentDto(
                    putter.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipmentReview.count(),
                    putter,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluatedCount,
                    equipment.evaluationMetricTotal
                )
            ).leftJoin(putter.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .where(putter.id.eq(id))
            .groupBy(putter.id)
            .fetchFirst()
}
