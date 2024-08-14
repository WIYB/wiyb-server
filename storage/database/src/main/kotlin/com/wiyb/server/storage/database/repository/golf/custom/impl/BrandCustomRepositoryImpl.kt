package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.wiyb.server.storage.database.entity.golf.Brand
import com.wiyb.server.storage.database.entity.golf.QBrand.brand
import com.wiyb.server.storage.database.entity.golf.dto.BrandDto
import com.wiyb.server.storage.database.entity.golf.dto.QBrandDto
import com.wiyb.server.storage.database.repository.golf.custom.BrandCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BrandCustomRepositoryImpl :
    QuerydslRepositorySupport(Brand::class.java),
    BrandCustomRepository {
    override fun findBrandList(): List<BrandDto> =
        from(brand)
            .select(
                QBrandDto(
                    brand.id.stringValue(),
                    brand.name,
                    brand.nameKo,
                    brand.imageUrl
                )
            ).fetch()
}
