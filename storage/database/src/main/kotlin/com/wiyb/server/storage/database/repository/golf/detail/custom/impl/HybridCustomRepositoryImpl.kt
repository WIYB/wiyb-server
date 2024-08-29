package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.Hybrid
import com.wiyb.server.storage.database.entity.golf.detail.QHybrid.hybrid
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.custom.HybridCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class HybridCustomRepositoryImpl :
    QuerydslRepositorySupport(Hybrid::class.java),
    HybridCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(hybrid)
            .select(
                QEquipmentDto(
                    hybrid.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedCount,
                    hybrid,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluationMetricTotal
                )
            ).leftJoin(hybrid.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(hybrid.id.eq(id))
            .groupBy(hybrid.id)
            .fetchFirst()
}
