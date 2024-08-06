package com.wiyb.server.core.domain.product

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto

data class IntegrateSearchDto(
    val users: List<UserSimpleProfileDto>,
    val equipments: List<EquipmentSimpleDto>
)
