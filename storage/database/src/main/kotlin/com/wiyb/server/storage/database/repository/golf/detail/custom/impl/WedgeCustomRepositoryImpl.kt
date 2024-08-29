package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.QWedge.wedge
import com.wiyb.server.storage.database.entity.golf.detail.Wedge
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.custom.WedgeCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class WedgeCustomRepositoryImpl :
    QuerydslRepositorySupport(Wedge::class.java),
    WedgeCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(wedge)
            .select(
                QEquipmentDto(
                    wedge.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedCount,
                    wedge,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluationMetricTotal
                )
            ).leftJoin(wedge.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(wedge.id.eq(id))
            .groupBy(wedge.id)
            .fetchFirst()
}
