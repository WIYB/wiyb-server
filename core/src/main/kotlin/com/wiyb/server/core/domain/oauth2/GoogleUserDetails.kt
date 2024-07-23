package com.wiyb.server.core.domain.oauth2

import com.wiyb.server.storage.entity.constant.SocialProvider

class GoogleUserDetails(
    private var attributes: MutableMap<String, Any>
) : OAuth2UserInfo {
    override fun getSocialProvider(): SocialProvider = SocialProvider.GOOGLE

    override fun getSocialId(): String = attributes.get("sub") as String

    override fun getEmail(): String = attributes.get("email") as String

    override fun getName(): String = attributes.get("name") as String
}
