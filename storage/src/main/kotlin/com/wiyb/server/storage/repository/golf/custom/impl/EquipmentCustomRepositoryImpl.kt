package com.wiyb.server.storage.repository.golf.custom.impl

import com.wiyb.server.storage.entity.golf.Equipment
import com.wiyb.server.storage.entity.golf.QBrand.brand
import com.wiyb.server.storage.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.entity.golf.dto.EquipmentSimpleInfoDto
import com.wiyb.server.storage.entity.golf.dto.QEquipmentSimpleInfoDto
import com.wiyb.server.storage.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentCustomRepositoryImpl :
    QuerydslRepositorySupport(Equipment::class.java),
    EquipmentCustomRepository {
    override fun findByNameKeyword(keyword: String): List<EquipmentSimpleInfoDto> =
        from(equipment)
            .select(QEquipmentSimpleInfoDto(equipment, brand.name, equipmentReview.countDistinct()))
            .leftJoin(equipment.brand, brand)
            .fetchJoin()
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .on(equipmentReview.deletedAt.isNull)
            .where(equipment.name.containsIgnoreCase(keyword))
            .fetch()
}
