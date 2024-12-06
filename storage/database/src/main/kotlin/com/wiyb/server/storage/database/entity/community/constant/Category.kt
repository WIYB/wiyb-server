package com.wiyb.server.storage.database.entity.community.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class Category(
    private val code: String,
    private val codeKo: String? = null
) : CodedEnum<String> {
    ALL("all", "전체 게시판"),
    GENERAL("general", "자유 게시판"),
    INFO("info", "정보 게시판"),
    QNA("qna", "질문/답변 게시판"),
    TIP("tip", "팁/레슨 게시판");

    companion object {
        fun find(code: String): Category? = entries.find { it.code.lowercase() == code.lowercase() }

        fun findKo(codeKo: String): Category? = entries.find { it.codeKo == codeKo }
    }

    override fun getCode(): String = code

    fun getCodeKo(): String? = codeKo

    @Converter(autoApply = true)
    class CategoryConverter : AbstractCodedEnumConverter<Category, String>(Category::class.java)
}
