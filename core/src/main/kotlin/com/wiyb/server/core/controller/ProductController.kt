package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.facade.ProductFacade
import com.wiyb.server.core.facade.ProductViewFacade
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.cache.entity.MostViewedProduct
import com.wiyb.server.storage.database.entity.golf.Brand
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentDetail
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.Difficulty
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.constant.Grip
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.repository.golf.BrandRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentDetailRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentReviewRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/product")
class ProductController(
    private val productFacade: ProductFacade,
    private val productViewFacade: ProductViewFacade,
    // todo: delete
    private val brandRepository: BrandRepository,
    private val equipmentRepository: EquipmentRepository,
    private val equipmentDetailRepository: EquipmentDetailRepository,
    private val equipmentReviewRepository: EquipmentReviewRepository,
    private val userService: UserService
) {
    @GetMapping("/most/view/simple")
    fun getMostViewed(): ResponseEntity<List<MostViewedProduct>> = ResponseEntity.ok().body(productViewFacade.getMostViewedProduct())

    @GetMapping("/{productId}/review")
    fun getProductReviews(
        @PathVariable("productId") productId: Long
    ): ResponseEntity<List<EquipmentReviewDto>> {
        val reviews = productFacade.getProductReviews(productId)
        return ResponseEntity.ok().body(reviews)
    }

    @PostMapping("/{productId}/review")
    fun postProductReview(
        @PathVariable("productId") productId: Long,
        @RequestBody dto: PostProductReviewDto
    ): ResponseEntity<Unit> {
        productFacade.postProductReview(productId, dto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable("productId") productId: Long
    ): ResponseEntity<EquipmentDto> {
        val productDetailDto = productFacade.getProductDetail(productId)
        return ResponseEntity.ok().body(productDetailDto)
    }

    // todo: delete
    @GetMapping("/test")
    fun test() {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)
        val brands = mutableListOf<Brand>()
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()
        val equipmentReviews = mutableListOf<EquipmentReview>()

        (1..20).map { i ->
            brands.add(Brand(name = "Brand $i", imageUrl = "https://autoimg.danawa.com/html/images/dMain/2024/0801/02.jpg"))
        }

        var index = 1
        EquipmentType.entries.forEach { type ->
            brands.forEach { brand ->
                (1..10).forEach { j ->
                    val equipment =
                        Equipment(
                            brand = brand,
                            type = type,
                            name = "${type.name} $j - Model $index",
                            releasedYear = "202${(1..9).random()}",
                            imageUrls = listOf("https://img.danawa.com/prod_img/500000/619/919/img/18919619_1.jpg")
                        )

                    equipments.add(equipment)
                    equipmentDetails.add(createEquipmentDetail(equipment))

                    val repeatCount = Random.nextInt(0, 31)

                    for (i in 1..repeatCount) {
                        val rating = getRating()

                        equipment.addEvaluationMetric(rating)
                        equipmentReviews.add(
                            EquipmentReview(
                                user = user,
                                equipment = equipment,
                                evaluationMetric = rating,
                                content = "\"${equipment.name}\" 의 ${i}번 째 리뷰",
                                imageUrls =
                                    listOf(
                                        "https://photo.akmall.com/image0/goods_comment/2023/8843331_1694699534058_1.jpg",
                                        "https://bampic.auction.co.kr/v1/840/587/d454587840/00645/d454587840240153064500.jpg"
                                    )
                            )
                        )
                    }

                    index++
                }
            }
        }

        brandRepository.saveAllAndFlush(brands)
        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
        equipmentReviewRepository.saveAllAndFlush(equipmentReviews)

        productViewFacade.init()
    }

    private fun createEquipmentDetail(equipment: Equipment): EquipmentDetail =
        EquipmentDetail(
            equipment = equipment,
            color = listOf("Red", "Black", "White", "Silver", "Blue").random(),
            // 200g ~ 500g,
            weight = Random.nextFloat() * 300 + 200,
            headProduceType = if (isHead(equipment.type)) listOf("Forged", "Casted").random() else null,
            headDesignType = if (isHead(equipment.type)) listOf("Modern", "Classic", "Aerodynamic").random() else null,
            headNumber = if (isHead(equipment.type)) Random.nextInt(1, 12) else null,
            headShape = if (isHead(equipment.type)) listOf("Round", "Square", "Teardrop").random() else null,
            headDifficulty = if (isHead(equipment.type)) Difficulty.entries.random() else null,
            headLoftDegree = if (isHead(equipment.type)) listOf("8.5", "9", "9.5", "10", "10.5", "11", "11.5", "12").random() else null,
            driverVolume = if (equipment.type == EquipmentType.DRIVER) Random.nextFloat() * 60 + 400 else null,
            iron7LoftDegree = if (equipment.type == EquipmentType.IRON) listOf("26", "28", "30", "32").random() else null,
            ironPLoftDegree = if (equipment.type == EquipmentType.IRON) listOf("42", "44", "46", "48").random() else null,
            putterNeckShape =
                if (equipment.type ==
                    EquipmentType.PUTTER
                ) {
                    listOf("Plumber's Neck", "Double Bend", "Short Slant", "Center Shafted").random()
                } else {
                    null
                },
            shaftStrength = if (isShaft(equipment.type)) listOf("Regular", "Stiff", "Extra Stiff", "Senior", "Ladies").random() else null,
            shaftKickPoint = if (isShaft(equipment.type)) listOf("Low", "Mid", "High").random() else null,
            shaftTorque = if (isShaft(equipment.type)) Random.nextFloat() * 4 + 2.0f else null,
            shaftTexture = if (isShaft(equipment.type)) listOf("Smooth", "Rough", "Matte", "Glossy").random() else null,
            gripType = if (isGrip(equipment.type)) Grip.entries.random() else null,
            gripRound = if (isGrip(equipment.type)) Random.nextFloat() * 2 + 58.0f else null,
            ballPiece = if (isBall(equipment.type)) listOf(2, 3, 4, 5).random() else null,
            ballCover = if (isBall(equipment.type)) listOf("Urethane", "Surlyn", "Ionomer").random() else null
        )

    private fun isHead(type: EquipmentType): Boolean =
        type == EquipmentType.DRIVER ||
            type == EquipmentType.WOOD ||
            type == EquipmentType.HYBRID ||
            type == EquipmentType.IRON ||
            type == EquipmentType.WEDGE ||
            type == EquipmentType.PUTTER

    private fun isShaft(type: EquipmentType): Boolean = type == EquipmentType.SHAFT

    private fun isGrip(type: EquipmentType): Boolean = type == EquipmentType.GRIP

    private fun isBall(type: EquipmentType): Boolean = type == EquipmentType.BALL

    private fun getRating(): List<Float> =
        listOf(
            Random.nextInt(1, 6).toFloat(),
            Random.nextInt(1, 6).toFloat(),
            Random.nextInt(1, 6).toFloat(),
            Random.nextInt(1, 6).toFloat(),
            Random.nextInt(1, 6).toFloat(),
            Random.nextInt(1, 6).toFloat()
        )
}
