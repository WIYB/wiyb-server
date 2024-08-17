package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.JPQLQuery
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchFilterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentCustomRepositoryImpl :
    QuerydslRepositorySupport(Equipment::class.java),
    EquipmentCustomRepository {
    override fun findSimpleById(id: Long): EquipmentSimpleDto? =
        from(equipment)
            .select(
                QEquipmentSimpleDto(
                    equipment.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.viewCount,
                    equipmentReview.count(),
                    equipment.releasedYear,
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .where(equipment.id.eq(id))
            .fetchFirst()

    override fun findReviewCounts(id: List<Long>): List<Long> =
        from(equipment)
            .select(equipmentReview.count())
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .where(equipment.id.`in`(id))
            .groupBy(equipment.id)
            .fetch()

    override fun findMostViewedProduct(
        type: EquipmentType?,
        limit: Long?
    ): List<EquipmentSimpleDto> {
        val query =
            from(equipment)
                .select(
                    QEquipmentSimpleDto(
                        equipment.id.stringValue(),
                        brand.name,
                        equipment.type,
                        equipment.name,
                        equipment.viewCount,
                        equipmentReview.count(),
                        equipment.releasedYear,
                        equipment.imageUrls
                    )
                ).leftJoin(equipment.brand, brand)
                .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
                .groupBy(equipment.id)
                .orderBy(equipment.viewCount.castToNum(Int::class.java).desc())
                .limit(limit ?: 10)

        if (type != null) {
            query.where(equipment.type.eq(type))
        }

        return query.fetch()
    }

    override fun findByIdList(idList: List<Long>): List<EquipmentSimpleDto> =
        from(equipment)
            .select(
                QEquipmentSimpleDto(
                    equipment.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.viewCount,
                    equipmentReview.count(),
                    equipment.releasedYear,
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .where(equipment.id.`in`(idList))
            .groupBy(equipment.id)
            .fetch()

    override fun findBySearchParameters(parameter: SearchParameterDto): List<EquipmentSimpleDto> = searchQuery(parameter).fetch()

    private fun searchQuery(parameter: SearchParameterDto): JPQLQuery<EquipmentSimpleDto> =
        from(equipment)
            .select(
                QEquipmentSimpleDto(
                    equipment.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.viewCount,
                    equipmentReview.count(),
                    equipment.releasedYear,
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .leftJoin(equipment.mutableEquipmentReviews, equipmentReview)
            .where(filterStrategy(parameter.filters))
            .groupBy(equipment.id)
            .orderBy(*sortStrategy(parameter.sortedBy))

    private fun filterStrategy(filters: SearchFilterDto): BooleanBuilder {
        val builder = BooleanBuilder()

        if (filters.brandNames.isNotEmpty()) {
            builder.and(equipment.brand.name.`in`(filters.brandNames.map { it.getCode() }))
        }
        if (filters.equipmentTypes.isNotEmpty()) {
            builder.and(equipment.type.`in`(filters.equipmentTypes))
        }
        if (filters.keywords.isNotEmpty()) {
            builder.andAnyOf(
                *filters.keywords.map { BooleanBuilder().or(equipment.name.containsIgnoreCase(it)) }.toTypedArray()
            )
        }

        return builder
    }

    private fun sortStrategy(sort: SearchSortedBy): Array<OrderSpecifier<Int>> {
        val strategy =
            arrayOf(
                equipmentReview.count().castToNum(Int::class.java).desc(),
                equipment.releasedYear.castToNum(Int::class.java).desc()
            )

        if (sort == SearchSortedBy.VIEW_COUNT_DESC) {
            return arrayOf(
                equipment.viewCount.castToNum(Int::class.java).desc(),
                *strategy
            )
        } else if (sort == SearchSortedBy.RELEASED_DESC) {
            strategy.reverse()
        } else if (sort == SearchSortedBy.RELEASED_ASC) {
            strategy[0] =
                equipment.releasedYear
                    .castToNum(Int::class.java)
                    .asc()
                    .nullsLast()
        }

        return strategy
    }
}
