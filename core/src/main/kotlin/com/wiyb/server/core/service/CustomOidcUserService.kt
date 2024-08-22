package com.wiyb.server.core.service

import com.wiyb.server.core.domain.auth.CustomOAuth2UserDetails
import com.wiyb.server.core.domain.auth.OAuth2UserInfo
import com.wiyb.server.storage.database.entity.auth.Account
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.constant.Role
import com.wiyb.server.storage.database.repository.auth.AccountRepository
import com.wiyb.server.storage.database.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) : OidcUserService() {
    @Transactional
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser: OidcUser = super.loadUser(userRequest)
        val provider: String = userRequest.clientRegistration.registrationId
        val oauth2UserInfo = OAuth2UserInfo.of(provider, oidcUser.attributes)
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

        return CustomOAuth2UserDetails(
            account.user,
            oidcUser.attributes,
            oidcUser.claims,
            oidcUser.userInfo,
            oidcUser.idToken
        )
    }
}
