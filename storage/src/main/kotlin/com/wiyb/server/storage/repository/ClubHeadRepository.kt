package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.ClubHead
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubHeadRepository : JpaRepository<ClubHead, Long>
