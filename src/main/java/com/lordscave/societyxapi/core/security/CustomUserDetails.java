package com.lordscave.societyxapi.core.security;

import com.lordscave.societyxapi.core.entity.User;
import com.lordscave.societyxapi.core.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // used as login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // can add logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // can add logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // can add logic if needed
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(UserStatus.ACTIVE);
    }
}

