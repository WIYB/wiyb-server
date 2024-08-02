package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.ClubShaftReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubShaftReviewRepository : JpaRepository<ClubShaftReview, Long>
