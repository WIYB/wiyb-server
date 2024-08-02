package com.wiyb.server.storage.repository.review

import com.wiyb.server.storage.entity.review.ClubHeadReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubHeadReviewRepository : JpaRepository<ClubHeadReview, Long>
