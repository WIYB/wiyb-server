package com.wiyb.server.core.domain.common

import com.github.f4b6a3.tsid.TsidCreator
import org.springframework.web.multipart.MultipartFile

data class UploadedImageDto(
    val name: String = TsidCreator.getTsid().toLong().toString(),
    val originName: String?,
    val contentType: String?,
    val contentLength: Long,
    val size: Long,
    var url: String? = null,
    var success: Boolean = false
) {
    companion object {
        fun fromFile(file: MultipartFile) =
            UploadedImageDto(
                originName = file.originalFilename,
                contentType = file.contentType,
                contentLength = file.inputStream.available().toLong(),
                size = file.size
            )
    }
}
