package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.QWood.wood
import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.QDriverMetric
import com.wiyb.server.storage.database.repository.golf.detail.custom.WoodCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class WoodCustomRepositoryImpl :
    QuerydslRepositorySupport(Wood::class.java),
    WoodCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(wood)
            .select(
                QEquipmentDto(
                    wood.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedMetric.evaluatedCount,
                    wood,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluatedMetric.evaluatedAverage,
                    QDriverMetric(
                        equipment.evaluatedMetric.forgivenessAverage,
                        equipment.evaluatedMetric.distanceAverage,
                        equipment.evaluatedMetric.accuracyAverage,
                        equipment.evaluatedMetric.impactFeelAverage,
                        equipment.evaluatedMetric.impactSoundAverage
                    )
                )
            ).leftJoin(wood.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(wood.id.eq(id))
            .groupBy(wood.id)
            .fetchFirst()
}
