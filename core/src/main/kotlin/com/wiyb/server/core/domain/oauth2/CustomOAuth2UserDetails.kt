package com.wiyb.server.core.domain.oauth2

import com.wiyb.server.storage.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2UserDetails(
    private val user: User,
    private var attributes: MutableMap<String, Any>
) : UserDetails,
    OAuth2User {
    override fun getAttributes(): MutableMap<String, Any> = attributes

    override fun getName(): String? = null

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        val collection: MutableCollection<GrantedAuthority> = ArrayList()
        collection.plus(GrantedAuthority { user.role.getRole() })

        return collection
    }

    override fun getUsername(): String? = user.nickname

    override fun getPassword(): String? = null

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
