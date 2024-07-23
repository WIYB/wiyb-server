package com.wiyb.server.core.domain.oauth2

import com.wiyb.server.storage.entity.constant.SocialProvider

interface OAuth2UserInfo {
    fun getSocialProvider(): SocialProvider

    fun getSocialId(): String

    fun getEmail(): String

    fun getName(): String
}
