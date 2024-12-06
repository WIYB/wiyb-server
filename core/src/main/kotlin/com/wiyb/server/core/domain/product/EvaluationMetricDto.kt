package com.wiyb.server.core.domain.product

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.metric.BaseMetric
import org.hibernate.validator.constraints.Range

data class EvaluationMetricDto(
    @field:Range(min = 1, max = 5)
    val forgiveness: Float? = null,
    @field:Range(min = 1, max = 5)
    val distance: Float? = null,
    @field:Range(min = 1, max = 5)
    val accuracy: Float? = null,
    @field:Range(min = 1, max = 5)
    val impactFeel: Float? = null,
    @field:Range(min = 1, max = 5)
    val impactSound: Float? = null,
    @field:Range(min = 1, max = 5)
    val backspin: Float? = null,
    @field:Range(min = 1, max = 5)
    val distanceControl: Float? = null,
    @field:Range(min = 1, max = 5)
    val stiffness: Float? = null,
    @field:Range(min = 1, max = 5)
    val weight: Float? = null,
    @field:Range(min = 1, max = 5)
    val trajectory: Float? = null,
    @field:Range(min = 1, max = 5)
    val touch: Float? = null,
    @field:Range(min = 1, max = 5)
    val gripComfort: Float? = null,
    @field:Range(min = 1, max = 5)
    val durability: Float? = null
) {
    fun flatten(type: EquipmentType): List<Float> {
        validateMetric(type)

        return when (type) {
            EquipmentType.DRIVER, EquipmentType.WOOD, EquipmentType.HYBRID ->
                BaseMetric.listPad(forgiveness!!, distance!!, accuracy!!, impactFeel!!, impactSound!!)

            EquipmentType.IRON, EquipmentType.WEDGE ->
                BaseMetric.listPad(forgiveness!!, distance!!, accuracy!!, impactFeel!!, backspin!!)

            EquipmentType.PUTTER ->
                BaseMetric.listPad(forgiveness!!, accuracy!!, distanceControl!!, impactFeel!!)

            EquipmentType.SHAFT ->
                BaseMetric.listPad(stiffness!!, weight!!, trajectory!!)

            EquipmentType.GRIP ->
                BaseMetric.listPad(touch!!, gripComfort!!, durability!!)

            else ->
                BaseMetric.listPad()
        }
    }

    private fun validateMetric(type: EquipmentType) {
        val isInvalid =
            when (type) {
                EquipmentType.DRIVER, EquipmentType.WOOD, EquipmentType.HYBRID ->
                    listOf(forgiveness, distance, accuracy, impactFeel, impactSound).any { it == null }

                EquipmentType.IRON, EquipmentType.WEDGE ->
                    listOf(forgiveness, distance, accuracy, impactFeel, backspin).any { it == null }

                EquipmentType.PUTTER ->
                    listOf(forgiveness, accuracy, distanceControl, impactFeel).any { it == null }

                EquipmentType.SHAFT ->
                    listOf(stiffness, weight, trajectory).any { it == null }

                EquipmentType.GRIP ->
                    listOf(touch, gripComfort, durability).any { it == null }

                else -> false
            }

        if (isInvalid) {
            throw CommonException(ErrorCode.INVALID_INPUT, "Invalid metric for $type")
        }
    }
}
