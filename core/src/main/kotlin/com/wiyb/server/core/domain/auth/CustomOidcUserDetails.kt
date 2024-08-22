package com.wiyb.server.core.domain.auth

import com.wiyb.server.storage.database.entity.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.Collections

data class CustomOidcUserDetails(
    val user: User,
    private var attributes: Map<String, Any>
) : UserDetails,
    OAuth2User,
    OidcUser {
    override fun getAttributes(): Map<String, Any> = attributes

    override fun getName(): String = user.id.toString()

    override fun getAuthorities(): Collection<GrantedAuthority?> = Collections.singletonList(SimpleGrantedAuthority(user.role.getRole()))

    override fun getClaims(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun getUserInfo(): OidcUserInfo {
        TODO("Not yet implemented")
    }

    override fun getIdToken(): OidcIdToken {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String = "unknown"

    override fun getPassword(): String? = null

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
