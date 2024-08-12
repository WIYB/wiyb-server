package com.wiyb.server.storage.database.entity.golf.detail.mapper

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.detail.Ball
import com.wiyb.server.storage.database.entity.golf.detail.Driver
import com.wiyb.server.storage.database.entity.golf.detail.Grip
import com.wiyb.server.storage.database.entity.golf.detail.Hybrid
import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.entity.golf.detail.Putter
import com.wiyb.server.storage.database.entity.golf.detail.Shaft
import com.wiyb.server.storage.database.entity.golf.detail.Wedge
import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractEquipmentDetail
import com.wiyb.server.storage.database.entity.golf.detail.dto.BallDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.DriverDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.GripDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.HybridDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.IronDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.PutterDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.ShaftDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.WedgeDto
import com.wiyb.server.storage.database.entity.golf.detail.dto.WoodDto

class EquipmentDetailMapper {
    companion object {
        fun invoke(entity: BaseEntity): AbstractEquipmentDetail =
            when (entity) {
                is Driver -> DriverDto(entity)
                is Wood -> WoodDto(entity)
                is Hybrid -> HybridDto(entity)
                is Iron -> IronDto(entity)
                is Wedge -> WedgeDto(entity)
                is Putter -> PutterDto(entity)
                is Shaft -> ShaftDto(entity)
                is Grip -> GripDto(entity)
                is Ball -> BallDto(entity)
                else -> throw IllegalArgumentException("Unknown equipment type: ${entity::class.simpleName}")
            }
    }
}
