package com.wiyb.server.storage.database.repository.golf

import com.wiyb.server.storage.DatabaseContextTest
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.repository.user.UserRepository
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import kotlin.random.Random
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EquipmentReviewRepositoryTest(
    private val userRepository: UserRepository,
    private val equipmentRepository: EquipmentRepository,
    private val equipmentReviewRepository: EquipmentReviewRepository
) : DatabaseContextTest() {
    @Test
    fun findWithPagination() {
        val user: User = userRepository.findById(617143977174827207).get()
        val equipment: Equipment = equipmentRepository.findById(616883225666527293).get()
        val reviews: MutableList<EquipmentReview> = mutableListOf()

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
