package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.entity.golf.detail.QIron.iron
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.QIronMetric
import com.wiyb.server.storage.database.repository.golf.detail.custom.IronCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class IronCustomRepositoryImpl :
    QuerydslRepositorySupport(Iron::class.java),
    IronCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(iron)
            .select(
                QEquipmentDto(
                    iron.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedMetric.evaluatedCount,
                    iron,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluatedMetric.evaluatedAverage,
                    QIronMetric(
                        equipment.evaluatedMetric.forgivenessAverage,
                        equipment.evaluatedMetric.distanceAverage,
                        equipment.evaluatedMetric.accuracyAverage,
                        equipment.evaluatedMetric.impactFeelAverage,
                        equipment.evaluatedMetric.backspinAverage
                    )
                )
            ).leftJoin(iron.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(iron.id.eq(id))
            .groupBy(iron.id)
            .fetchFirst()
}
