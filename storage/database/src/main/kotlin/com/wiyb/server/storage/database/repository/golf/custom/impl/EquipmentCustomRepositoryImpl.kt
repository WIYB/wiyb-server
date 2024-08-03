package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberPath
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleInfoDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentSimpleInfoDto
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentCustomRepositoryImpl :
    QuerydslRepositorySupport(Equipment::class.java),
    EquipmentCustomRepository {
    override fun findByNameKeyword(keyword: String): List<EquipmentSimpleInfoDto> {
        val reviewCount: NumberPath<Long> = Expressions.numberPath(Long::class.java, "reviewCount")

        return from(equipment)
            .select(
                QEquipmentSimpleInfoDto(
                    brand.id.stringValue(),
                    equipment.type,
                    equipment.name,
                    equipment.releasedYear,
                    equipmentReview.count().`as`(reviewCount),
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .fetchJoin()
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .where(equipment.name.containsIgnoreCase(keyword))
            .groupBy(equipment.type, equipment.id)
            .orderBy(
                equipment.type.count().desc(),
                reviewCount.desc()
            ).fetch()
    }
}
