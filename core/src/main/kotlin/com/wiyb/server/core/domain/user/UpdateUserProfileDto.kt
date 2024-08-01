package com.wiyb.server.core.domain.user

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.entity.UserProfile
import com.wiyb.server.storage.entity.constant.user.Gender
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import java.time.LocalDate

data class UpdateUserProfileDto(
    @field:Range(min = 2, max = 10, message = "nickname must be at least 2 characters and at most 10 characters")
    val nickname: String? = null,
    @field:ValueOfEnum(enumClass = Gender::class, message = "gender value is invalid")
    val gender: String? = null,
    @field:Past(message = "birth must be past")
    val birth: LocalDate? = null,
    @field:Positive(message = "handy must be positive")
    val handy: Int? = null,
    @field:Positive(message = "height must be positive")
    val height: Int? = null,
    @field:Positive(message = "weight must be positive")
    val weight: Int? = null,
    @field:URL(message = "imageUrl must be url")
    val imageUrl: String? = null
) {
    fun updateEntity(entity: UserProfile): UserProfile {
        entity.update(
            nickname = this.nickname ?: entity.nickname,
            gender = enumValueOf<Gender>((gender ?: entity.gender.getCode()).uppercase()),
            birth = this.birth ?: entity.birth,
            handy = this.handy ?: entity.handy,
            height = this.height ?: entity.height,
            weight = this.weight ?: entity.weight,
            imageUrl = this.imageUrl ?: entity.imageUrl
        )

        return entity
    }
}
