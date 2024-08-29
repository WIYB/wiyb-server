package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.JPQLQuery
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchFilterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentCustomRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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
                    equipment.evaluatedCount,
                    equipment.evaluationMetricTotal,
                    equipment.releasedYear,
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .where(equipment.id.eq(id))
            .fetchFirst()

    override fun findReviewCounts(id: List<Long>): List<Long> =
        from(equipment)
            .select(equipment.evaluatedCount)
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
                        equipment.evaluatedCount,
                        equipment.evaluationMetricTotal,
                        equipment.releasedYear,
                        equipment.imageUrls
                    )
                ).leftJoin(equipment.brand, brand)
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
                    equipment.evaluatedCount,
                    equipment.evaluationMetricTotal,
                    equipment.releasedYear,
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .where(equipment.id.`in`(idList))
            .groupBy(equipment.id)
            .fetch()

    override fun findBySearchParameters(parameter: SearchParameterDto): PaginationResultDto<EquipmentSimpleDto> {
        val pageRequest = parameter.page.of()
        val query = searchQuery(parameter, pageRequest)
        val page = PageImpl(query.fetch(), pageRequest, query.fetchCount())

        return PaginationResultDto.fromPage<EquipmentSimpleDto>(parameter.page.contextId, page)
    }

    private fun searchQuery(
        parameter: SearchParameterDto,
        pageRequest: PageRequest
    ): JPQLQuery<EquipmentSimpleDto> =
        from(equipment)
            .select(
                QEquipmentSimpleDto(
                    equipment.id.stringValue(),
                    brand.name,
                    equipment.type,
                    equipment.name,
                    equipment.viewCount,
                    equipment.evaluatedCount,
                    equipment.evaluationMetricTotal,
                    equipment.releasedYear,
                    equipment.imageUrls
                )
            ).leftJoin(equipment.brand, brand)
            .where(filterStrategy(parameter.filters))
            .groupBy(equipment)
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .orderBy(*paginationStrategy(parameter.page.sortedBy))

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

    private fun paginationStrategy(sort: SearchSortedBy): Array<OrderSpecifier<Int>> =
        when (sort) {
            SearchSortedBy.VIEW_COUNT_DESC ->
                arrayOf(
                    equipment.viewCount.castToNum(Int::class.java).desc(),
                    equipment.evaluatedCount.castToNum(Int::class.java).desc(),
                    equipment.releasedYear.castToNum(Int::class.java).desc()
                )

            SearchSortedBy.RELEASED_DESC ->
                arrayOf(
                    equipment.releasedYear.castToNum(Int::class.java).desc(),
                    equipment.evaluatedCount.castToNum(Int::class.java).desc()
                )

            SearchSortedBy.RELEASED_ASC ->
                arrayOf(
                    equipment.releasedYear.castToNum(Int::class.java).asc(),
                    equipment.evaluatedCount.castToNum(Int::class.java).desc()
                )

            else ->
                arrayOf(
                    equipment.evaluatedCount.castToNum(Int::class.java).desc(),
                    equipment.releasedYear.castToNum(Int::class.java).desc()
                )
        }
}
