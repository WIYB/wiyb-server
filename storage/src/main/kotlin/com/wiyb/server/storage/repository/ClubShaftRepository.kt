package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.golf.ClubShaft
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubShaftRepository : JpaRepository<ClubShaft, Long>
