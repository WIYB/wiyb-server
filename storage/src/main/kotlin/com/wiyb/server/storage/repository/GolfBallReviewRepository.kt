package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.ClubHeadReview
import com.wiyb.server.storage.entity.GolfBallReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GolfBallReviewRepository : JpaRepository<GolfBallReview, Long>
