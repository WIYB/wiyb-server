package com.wiyb.server.core.service

import com.wiyb.server.core.domain.oauth2.CustomOAuth2UserDetails
import com.wiyb.server.core.domain.oauth2.GoogleUserDetails
import com.wiyb.server.core.domain.oauth2.OAuth2UserInfo
import com.wiyb.server.storage.entity.Account
import com.wiyb.server.storage.entity.User
import com.wiyb.server.storage.entity.constant.Role
import com.wiyb.server.storage.entity.constant.SocialProvider
import com.wiyb.server.storage.repository.AccountRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val accountRepository: AccountRepository
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oauth2User: OAuth2User = super.loadUser(userRequest)
        val provider: String = userRequest.clientRegistration.registrationId

        val oauth2UserInfo: OAuth2UserInfo =
            if (provider == SocialProvider.GOOGLE.getCode()) {
                GoogleUserDetails(oauth2User.attributes)
            } else {
                TODO("예외 처리가 필요할 지 고민")
            }

        // todo: with User
        var account: Account? = accountRepository.findByEmail(oauth2UserInfo.getEmail())

        if (account == null) {
            val newUser =
                User(
                    role = Role.USER,
                    name = oauth2UserInfo.getName()
                )
            val newAccount =
                Account(
                    socialProvider = oauth2UserInfo.getSocialProvider(),
                    socialId = oauth2UserInfo.getSocialId(),
                    email = oauth2UserInfo.getEmail(),
                    user = newUser
                )
            accountRepository.save(newAccount)
            account = newAccount
        }

        return CustomOAuth2UserDetails(account.user, oauth2User.attributes)
    }
}
