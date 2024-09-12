package com.wiyb.server.storage.database.repository.community.custom

import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.community.dto.PostDto
import com.wiyb.server.storage.database.entity.community.dto.PostPaginationDto
import java.util.Optional

interface PostCustomRepository {
    fun findDetailById(id: Long): Optional<PostDto>

    fun findPage(parameter: PostPaginationDto): PaginationResultDto<PostDto>
}
