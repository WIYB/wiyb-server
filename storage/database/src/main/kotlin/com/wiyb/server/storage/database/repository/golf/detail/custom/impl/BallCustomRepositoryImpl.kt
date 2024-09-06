package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.Ball
import com.wiyb.server.storage.database.entity.golf.detail.QBall.ball
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.QNotSupportedMetric
import com.wiyb.server.storage.database.repository.golf.detail.custom.BallCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BallCustomRepositoryImpl :
    QuerydslRepositorySupport(Ball::class.java),
    BallCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(ball)
            .select(
                QEquipmentDto(
                    ball.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedMetric.evaluatedCount,
                    ball,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluatedMetric.evaluatedAverage,
                    QNotSupportedMetric(
                        equipment.evaluatedMetric.evaluatedAverage
                    )
                )
            ).leftJoin(ball.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(ball.id.eq(id))
            .groupBy(ball.id)
            .fetchFirst()
}
