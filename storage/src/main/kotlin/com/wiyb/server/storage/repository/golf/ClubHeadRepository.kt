package com.wiyb.server.storage.repository.golf

import com.wiyb.server.storage.entity.golf.ClubHead
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubHeadRepository : JpaRepository<ClubHead, Long>
