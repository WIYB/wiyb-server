package com.wiyb.server.core.service

import com.wiyb.server.storage.repository.golf.ClubGripRepository
import com.wiyb.server.storage.repository.golf.ClubHeadRepository
import com.wiyb.server.storage.repository.golf.ClubShaftRepository
import com.wiyb.server.storage.repository.golf.GolfBallRepository
import com.wiyb.server.storage.repository.golf.GolfOtherEquipmentRepository
import com.wiyb.server.storage.repository.user.UserProfileRepository
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
