package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.dto.BrandDto

interface BrandCustomRepository {
    fun findBrandList(): List<BrandDto>
}
