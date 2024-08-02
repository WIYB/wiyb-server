package com.wiyb.server.storage.repository.review

import com.wiyb.server.storage.entity.review.ClubGripReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubGripReviewRepository : JpaRepository<ClubGripReview, Long>
