package com.wiyb.server.storage.database.repository.golf.detail.custom.impl

import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.detail.Driver
import com.wiyb.server.storage.database.entity.golf.detail.QDriver.driver
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.custom.DriverCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class DriverCustomRepositoryImpl :
    QuerydslRepositorySupport(Driver::class.java),
    DriverCustomRepository {
    override fun findDetailById(id: Long): EquipmentDto? =
        from(driver)
            .select(
                QEquipmentDto(
                    driver.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.evaluatedCount,
                    driver,
                    equipment.releasedYear,
                    equipment.imageUrls,
                    equipment.viewCount,
                    equipment.evaluationMetricTotal
                )
            ).leftJoin(driver.equipment, equipment)
            .leftJoin(equipment.brand, brand)
            .where(driver.id.eq(id))
            .groupBy(driver.id)
            .fetchFirst()
}
