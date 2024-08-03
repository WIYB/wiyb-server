package com.wiyb.server.core.domain.golf

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleInfoDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto

data class IntegrateSearchDto(
    val users: List<UserSimpleProfileDto>,
    val equipments: List<EquipmentSimpleInfoDto>
)
