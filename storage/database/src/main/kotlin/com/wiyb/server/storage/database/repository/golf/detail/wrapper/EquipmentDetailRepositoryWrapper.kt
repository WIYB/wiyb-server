package com.wiyb.server.storage.database.repository.golf.detail.wrapper

import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractEquipmentDetail
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.repository.golf.detail.BallRepository
import com.wiyb.server.storage.database.repository.golf.detail.DriverRepository
import com.wiyb.server.storage.database.repository.golf.detail.GripRepository
import com.wiyb.server.storage.database.repository.golf.detail.HybridRepository
import com.wiyb.server.storage.database.repository.golf.detail.IronRepository
import com.wiyb.server.storage.database.repository.golf.detail.PutterRepository
import com.wiyb.server.storage.database.repository.golf.detail.ShaftRepository
import com.wiyb.server.storage.database.repository.golf.detail.WedgeRepository
import com.wiyb.server.storage.database.repository.golf.detail.WoodRepository
import com.wiyb.server.storage.database.repository.golf.detail.custom.EquipmentDetailCustomRepository
import org.springframework.stereotype.Component

@Component
class EquipmentDetailRepositoryWrapper(
    private val driverRepository: DriverRepository,
    private val woodRepository: WoodRepository,
    private val hybridRepository: HybridRepository,
    private val ironRepository: IronRepository,
    private val wedgeRepository: WedgeRepository,
    private val putterRepository: PutterRepository,
    private val shaftRepository: ShaftRepository,
    private val gripRepository: GripRepository,
    private val ballRepository: BallRepository
) {
    private val repository: Map<EquipmentType, EquipmentDetailCustomRepository<out AbstractEquipmentDetail>> =
        mapOf(
            EquipmentType.DRIVER to driverRepository,
            EquipmentType.WOOD to woodRepository,
            EquipmentType.HYBRID to hybridRepository,
            EquipmentType.IRON to ironRepository,
            EquipmentType.WEDGE to wedgeRepository,
            EquipmentType.PUTTER to putterRepository,
            EquipmentType.SHAFT to shaftRepository,
            EquipmentType.GRIP to gripRepository,
            EquipmentType.BALL to ballRepository
        )

    fun findDetailById(
        id: Long,
        type: EquipmentType
    ): EquipmentDto? = repository[type]?.findDetailById(id)
}
