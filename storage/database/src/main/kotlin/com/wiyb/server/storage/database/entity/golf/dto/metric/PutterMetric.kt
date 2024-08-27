package com.wiyb.server.storage.database.entity.golf.dto.metric

data class PutterMetric(
    override val forgiveness: Float,
    override val accuracy: Float,
    override val distanceControl: Float,
    override val impactFeel: Float
) : BaseMetric() {
    override fun flatten(): List<Float> = listPad(forgiveness, accuracy, distanceControl, impactFeel)
}
