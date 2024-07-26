package com.wiyb.server.core.domain.exception

import com.google.gson.JsonObject
import java.time.LocalDateTime

open class CommonException(
    private val errorCode: ErrorCode,
    override val message: String? = errorCode.message
) : RuntimeException(message) {
    private val timestamp: LocalDateTime = LocalDateTime.now()

    fun toJson(): String {
        val json = JsonObject()
        json.addProperty("status", errorCode.httpStatus.value())
        json.addProperty("code", errorCode.code)
        json.addProperty("identifier", errorCode.name)
        json.addProperty("message", message)
        json.addProperty("timestamp", timestamp.toString())

        return json.toString()
    }

    fun getStatus(): Int = errorCode.httpStatus.value()

    companion object {
        fun fromInternalException(e: Exception): CommonException =
            CommonException(ErrorCode.UNKNOWN_SERVER_ERROR, e.message ?: ErrorCode.UNKNOWN_SERVER_ERROR.message)
    }
}
