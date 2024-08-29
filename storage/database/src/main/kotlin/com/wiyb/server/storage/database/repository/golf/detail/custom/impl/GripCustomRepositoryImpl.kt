package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.Grip
import com.wiyb.server.storage.database.entity.golf.detail.QGrip.grip
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.custom.GripCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class GripCustomRepositoryImpl :
    QuerydslRepositorySupport(Grip::class.java),
    GripCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(grip)
            .select(
                QEquipmentDto(
                    grip.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedCount,
                    grip,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluationMetricTotal
                )
            ).leftJoin(grip.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(grip.id.eq(id))
            .groupBy(grip.id)
            .fetchFirst()
}
