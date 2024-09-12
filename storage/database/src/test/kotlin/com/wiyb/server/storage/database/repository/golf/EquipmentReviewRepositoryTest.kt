package com.wiyb.server.storage.database.repository.golf

import com.wiyb.server.storage.DatabaseContextTest
import com.wiyb.server.storage.database.entity.golf.Brand
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.constant.Role
import com.wiyb.server.storage.database.repository.user.UserRepository
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EquipmentReviewRepositoryTest(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val brandRepository: BrandRepository,
    @Autowired private val equipmentRepository: EquipmentRepository,
    @Autowired private val equipmentReviewRepository: EquipmentReviewRepository
) : DatabaseContextTest() {
    @Test
    fun findWithPagination() {
        val user = User(Role.USER)
        val brand = Brand("test_brand")
        val equipment =
            Equipment(
                brand = brand,
                type = EquipmentType.PUTTER,
                name = "test_equipment"
            )
        val reviews: MutableList<EquipmentReview> = mutableListOf()

        userRepository.saveAndFlush(user)
        brandRepository.saveAndFlush(brand)
        equipmentRepository.saveAndFlush(equipment)

        for (i in 1..151) {
            val review =
                EquipmentReview(
                    user = user,
                    equipment = equipment,
                    likeCount = Random.nextInt(0, 100),
                    evaluationMetric = List(5) { Random.nextDouble(1.0, 5.0).toFloat() },
                    content = "content $i",
                    imageUrls = listOf("https://wiybuck.s3.ap-northeast-2.amazonaws.com/617149697112539024")
                )
            reviews.add(review)
        }

        equipmentReviewRepository.saveAllAndFlush(reviews)
    }
}
