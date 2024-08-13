package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.JPQLQuery
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchFilterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchFilterDtoV2
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDtoV2
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentCustomRepositoryImpl :
    QuerydslRepositorySupport(Equipment::class.java),
    EquipmentCustomRepository {
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

    override fun findBySearchParameters(
        keyword: String?,
        filters: SearchFilterDto,
        sort: SearchSortedBy
    ): List<EquipmentSimpleDto> = searchQuery(keyword, filters, sort).fetch()

    override fun findBySearchParametersV2(parameter: SearchParameterDtoV2): List<EquipmentSimpleDto> = searchQueryV2(parameter).fetch()

    private fun searchQuery(
        keyword: String? = null,
        filters: SearchFilterDto,
        sort: SearchSortedBy
    ): JPQLQuery<EquipmentSimpleDto> =
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
            .where(filterStrategy(keyword, filters))
            .groupBy(equipment.id)
            .orderBy(*sortStrategy(sort))

    private fun filterStrategy(
        keyword: String?,
        filters: SearchFilterDto
    ): BooleanBuilder {
        val builder = BooleanBuilder()

        if (filters.brandNames.isNotEmpty()) {
            builder.and(equipment.brand.name.`in`(filters.brandNames.map { it.getCode() }))
        }
        if (filters.equipmentTypes.isNotEmpty()) {
            builder.and(equipment.type.`in`(filters.equipmentTypes))
        }
        if (keyword != null) {
            builder.and(
                equipment.name
                    .containsIgnoreCase(keyword)
                    .or(brand.name.containsIgnoreCase(keyword))
                    .or(brand.nameKo.containsIgnoreCase(keyword))
            )
        }

        println("\n============================\n")
        println(filters.brandNames)
        println(filters.equipmentTypes)
        println("\n============================\n")

        return builder
    }

    private fun sortStrategy(sort: SearchSortedBy): Array<OrderSpecifier<Int>> {
        val strategy =
            arrayOf(
                equipmentReview.count().castToNum(Int::class.java).desc(),
                equipment.releasedYear.castToNum(Int::class.java).desc()
            )

        if (sort == SearchSortedBy.RELEASED_DESC) {
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

    private fun searchQueryV2(parameter: SearchParameterDtoV2): JPQLQuery<EquipmentSimpleDto> =
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
            .where(filterStrategyV2(parameter.filters))
            .groupBy(equipment.id)
            .orderBy(*sortStrategyV2(parameter.sortedBy))

    private fun filterStrategyV2(filters: SearchFilterDtoV2): BooleanBuilder {
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

    private fun sortStrategyV2(sort: SearchSortedBy): Array<OrderSpecifier<Int>> {
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
