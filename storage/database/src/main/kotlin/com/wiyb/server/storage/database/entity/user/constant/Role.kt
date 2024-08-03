package com.wiyb.server.storage.database.entity.user.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class Role(
    private val code: String,
    private val role: String
) : CodedEnum<String> {
    ADMIN("admin", "ROLE_ADMIN"),
    USER("user", "ROLE_USER"),
    GUEST("guest", "ROLE_GUEST");

    override fun getCode(): String = code

    fun getRole(): String = role

    @Converter(autoApply = true)
    class RoleConverter : AbstractCodedEnumConverter<Role, String>(Role::class.java)
}
