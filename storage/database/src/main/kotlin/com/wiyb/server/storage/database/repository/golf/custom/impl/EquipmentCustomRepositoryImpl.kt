package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberPath
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentDetail.equipmentDetail
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDetailDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentDetailDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentSimpleDto
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentCustomRepositoryImpl :
    QuerydslRepositorySupport(Equipment::class.java),
    EquipmentCustomRepository {
    override fun findByNameKeyword(keyword: String): List<EquipmentSimpleDto> {
        val reviewCount: NumberPath<Long> = Expressions.numberPath(Long::class.java, "reviewCount")

        val query =
            from(equipment)
                .select(
                    QEquipmentSimpleDto(
                        brand.id.stringValue(),
                        equipment.type,
                        equipment.name,
                        equipment.releasedYear,
                        equipmentReview.count().`as`(reviewCount),
                        equipment.imageUrls
                    )
                ).leftJoin(equipment.brand, brand)
                .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
                .where(equipment.name.containsIgnoreCase(keyword))
                .groupBy(equipment.type, equipment.id)
                .orderBy(
                    equipment.type.count().desc(),
                    equipment.releasedYear.castToNum(Int::class.java).desc(),
                    reviewCount.desc()
                )
        return query.fetch()
    }

    override fun findOneWithDetailById(id: Long): EquipmentDetailDto? =
        from(equipment)
            .select(QEquipmentDetailDto(brand, equipment, equipmentDetail, equipment.mutableEquipmentReviews))
            .leftJoin(equipment.brand, brand)
            .fetchJoin()
            .leftJoin(equipment.equipmentDetail, equipmentDetail)
            .fetchJoin()
            .leftJoin(equipment.mutableEquipmentReviews)
            .fetchJoin()
            .where(equipment.id.eq(id))
            .fetchOne()
}
