package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.GolfBall
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GolfBallRepository : JpaRepository<GolfBall, Long>
