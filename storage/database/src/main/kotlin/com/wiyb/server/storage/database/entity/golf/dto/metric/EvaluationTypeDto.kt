package com.wiyb.server.storage.database.entity.golf.dto.metric

import com.wiyb.server.storage.database.entity.golf.dto.metric.constant.EvaluationType

data class EvaluationTypeDto(
    val name: String,
    val nameKo: String
) {
    companion object {
        fun toDto(type: EvaluationType): EvaluationTypeDto = EvaluationTypeDto(type.getCode(), type.getCodeKo())

        fun toDtoList(): List<EvaluationTypeDto> = EvaluationType.entries.map { toDto(it) }
    }
}
