package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.QShaft.shaft
import com.wiyb.server.storage.database.entity.golf.detail.Shaft
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.custom.ShaftCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShaftCustomRepositoryImpl :
    QuerydslRepositorySupport(Shaft::class.java),
    ShaftCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(shaft)
            .select(
                QEquipmentDto(
                    shaft.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedCount,
                    shaft,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluationMetricTotal
                )
            ).leftJoin(shaft.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(shaft.id.eq(id))
            .groupBy(shaft.id)
            .fetchFirst()
}
