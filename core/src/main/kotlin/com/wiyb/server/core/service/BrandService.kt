package com.wiyb.server.core.service

import com.wiyb.server.storage.database.repository.golf.BrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {
    fun findBrandList() = brandRepository.findBrandList()
}
