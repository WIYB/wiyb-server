package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.common.UploadImageDto
import com.wiyb.server.core.domain.common.UploadedImageDto
import com.wiyb.server.core.service.ImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/image")
@RestController
class ImageController(
    private val imageService: ImageService
) {
    @PostMapping
    fun uploadImage(
        @ModelAttribute uploadImageDto: UploadImageDto
    ): ResponseEntity<List<UploadedImageDto>> = ResponseEntity.ok().body(imageService.uploadImages(uploadImageDto))
}
