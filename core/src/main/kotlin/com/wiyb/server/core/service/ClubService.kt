package com.wiyb.server.core.service

import com.wiyb.server.storage.repository.ClubGripRepository
import com.wiyb.server.storage.repository.ClubHeadRepository
import com.wiyb.server.storage.repository.ClubShaftRepository
import com.wiyb.server.storage.repository.GolfBallRepository
import com.wiyb.server.storage.repository.GolfOtherEquipmentRepository
import com.wiyb.server.storage.repository.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class ClubService(
    private val userProfileRepository: UserProfileRepository,
    private val clubHeadRepository: ClubHeadRepository,
    private val clubShaftRepository: ClubShaftRepository,
    private val clubGripRepository: ClubGripRepository,
    private val golfBallRepository: GolfBallRepository,
    private val golfOtherEquipmentRepository: GolfOtherEquipmentRepository
)
