package com.wiyb.server.core.domain.auth

import com.wiyb.server.storage.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

data class CustomOAuth2UserDetails(
    val user: User,
    private var attributes: Map<String, Any>
) : UserDetails,
    OAuth2User {
    override fun getAttributes(): Map<String, Any> = attributes

    override fun getName(): String = user.name

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val collection: Collection<GrantedAuthority> = ArrayList()
        collection.plus(GrantedAuthority { user.role.getRole() })

        return collection
    }

    override fun getUsername(): String = user.nickname ?: "unknown"

    override fun getPassword(): String? = null

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
