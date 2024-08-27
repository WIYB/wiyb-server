package com.wiyb.server.storage.database.entity.golf.dto.metric

class NotSupportedMetric : BaseMetric() {
    override fun flatten(): List<Float> = listPad()
}
