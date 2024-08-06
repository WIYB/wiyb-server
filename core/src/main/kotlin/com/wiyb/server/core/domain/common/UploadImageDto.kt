package com.wiyb.server.core.domain.common

import com.wiyb.server.core.config.annotation.NotBlankElementList
import org.springframework.web.multipart.MultipartFile

data class UploadImageDto(
    @field:NotBlankElementList
    val images: List<MultipartFile>
)
