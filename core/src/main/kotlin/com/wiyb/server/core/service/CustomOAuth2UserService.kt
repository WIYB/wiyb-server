package com.wiyb.server.core.service

import com.wiyb.server.core.domain.auth.CustomOAuth2UserDetails
import com.wiyb.server.core.domain.auth.OAuth2UserInfo
import com.wiyb.server.storage.database.entity.auth.Account
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.constant.Role
import com.wiyb.server.storage.database.repository.auth.AccountRepository
import com.wiyb.server.storage.database.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) : DefaultOAuth2UserService() {
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val attributes: MutableMap<String, Any> = super.loadUser(userRequest).attributes
        val provider: String = userRequest.clientRegistration.registrationId
        val oauth2UserInfo = OAuth2UserInfo.of(provider, attributes)
        var account: Account? = accountRepository.findBySocialIdWithUser(oauth2UserInfo.socialId)

        if (account == null) {
            val newUser = User(role = Role.GUEST)
            val newAccount =
                Account(
                    socialProvider = oauth2UserInfo.socialProvider,
                    socialId = oauth2UserInfo.socialId,
                    email = oauth2UserInfo.email,
                    user = newUser
                )
            userRepository.save(newUser)
            accountRepository.save(newAccount)
            account = newAccount
        }

        return CustomOAuth2UserDetails(account.user, attributes)
    }
}
