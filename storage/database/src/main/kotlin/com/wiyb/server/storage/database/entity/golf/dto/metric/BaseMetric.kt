package com.wiyb.server.storage.database.entity.golf.dto.metric

import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

abstract class BaseMetric : EvaluatedMetric {
    override val forgiveness: Float? = null
    override val distance: Float? = null
    override val accuracy: Float? = null
    override val impactFeel: Float? = null
    override val impactSound: Float? = null
    override val backSpin: Float? = null
    override val distanceControl: Float? = null
    override val stiffness: Float? = null
    override val weight: Float? = null
    override val trajectory: Float? = null
    override val touch: Float? = null
    override val gripComfort: Float? = null
    override val durability: Float? = null

    abstract fun flatten(): List<Float>

    companion object {
        fun listPad(vararg elements: Float): List<Float> =
            if (elements.size < 5) {
                elements.toList() + List(5 - elements.size) { 0f }
            } else {
                elements.toList()
            }

        fun expand(
            type: EquipmentType,
            data: List<Float>
        ): BaseMetric {
            // 무조건 길이 5인 List
            if (data.size != 5) throw IllegalArgumentException("데이터 개수가 5개가 아님")

            return when (type) {
                EquipmentType.DRIVER, EquipmentType.WOOD, EquipmentType.HYBRID ->
                    DriverMetric(
                        forgiveness = data[0],
                        distance = data[1],
                        accuracy = data[2],
                        impactFeel = data[3],
                        impactSound = data[4]
                    )

                EquipmentType.IRON, EquipmentType.WEDGE ->
                    IronMetric(
                        forgiveness = data[0],
                        distance = data[1],
                        accuracy = data[2],
                        impactFeel = data[3],
                        backSpin = data[4]
                    )

                EquipmentType.PUTTER ->
                    PutterMetric(
                        forgiveness = data[0],
                        accuracy = data[1],
                        distanceControl = data[2],
                        impactFeel = data[3]
                    )

                EquipmentType.SHAFT ->
                    ShaftMetric(
                        stiffness = data[0],
                        weight = data[1],
                        trajectory = data[2]
                    )

                EquipmentType.GRIP ->
                    GripMetric(
                        touch = data[0],
                        gripComfort = data[1],
                        durability = data[2]
                    )

                else -> NotSupportedMetric()
            }
        }
    }
}
