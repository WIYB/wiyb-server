package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.ClubGrip
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubGripRepository : JpaRepository<ClubGrip, Long>
