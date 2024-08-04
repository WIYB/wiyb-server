package com.wiyb.server.core.controller

import com.wiyb.server.core.facade.ProductFacade
import com.wiyb.server.storage.database.entity.golf.Brand
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDetailDto
import com.wiyb.server.storage.database.repository.golf.BrandRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    private val productFacade: ProductFacade,
    private val brandRepository: BrandRepository,
    private val equipmentRepository: EquipmentRepository
) {
    @GetMapping("/{productId}")
    fun getProductDetail(
        // todo: 쿼리스트링 validation 안됨
        @PathVariable("productId") productId: Long
    ): ResponseEntity<EquipmentDetailDto> {
        val productDetailDto = productFacade.getProductDetail(productId)
        return ResponseEntity.ok().body(productDetailDto)
    }

    @GetMapping("/test")
    fun test() {
        val brands = mutableListOf<Brand>()
        val equipments = mutableListOf<Equipment>()

        (1..20).map { i ->
            brands.add(Brand(name = "Brand $i", imageUrl = "http://example.com/brand$i.png"))
        }

        var index = 1
        EquipmentType.entries.forEach { type ->
            brands.forEach { brand ->
                (1..10).forEach { j ->
                    equipments.add(
                        Equipment(
                            brand = brand,
                            type = type,
                            name = "${type.name} $j - Model $index",
                            releasedYear = "202${(1..9).random()}",
                            imageUrls = listOf("http://example.com/equipment/${type.name.toLowerCase()}/$j.png")
                        )
                    )
                    index++
                }
            }
        }

        brandRepository.saveAll(brands)
        equipmentRepository.saveAll(equipments)
    }
}
