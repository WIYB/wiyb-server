package com.wiyb.server.core.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.wiyb.server.core.domain.common.UploadImageDto
import com.wiyb.server.core.domain.common.UploadedImageDto
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val amazonS3Client: AmazonS3Client
) {
    private val bucketName = "wiybuck"

    fun uploadImages(dto: UploadImageDto): List<UploadedImageDto> = dto.images.map { uploadImage(it) }

    fun uploadImage(multipartFile: MultipartFile): UploadedImageDto {
        val dto = UploadedImageDto.fromFile(multipartFile)

        try {
            val objectMetadata = ObjectMetadata()
            objectMetadata.contentType = dto.contentType
            objectMetadata.contentLength = dto.contentLength

            amazonS3Client.putObject(bucketName, dto.name, multipartFile.inputStream, objectMetadata)

            val url = amazonS3Client.getUrl(bucketName, dto.name).toString()
            dto.url = url
            dto.success = true
        } catch (_: Exception) {
        }

        return dto
    }
}
