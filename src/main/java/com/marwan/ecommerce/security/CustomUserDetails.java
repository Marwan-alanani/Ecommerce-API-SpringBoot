package com.marwan.ecommerce.security;

import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails
{
    private final User user;

    public UUID getUserId()
    {
        return user.getUserId();
    }

    @Override
    @NullMarked
    public String getUsername()
    {
        return user.getEmail();
    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    public UserRole getRole()
    {
        return user.getRole();
    }

    @Override
    public boolean isEnabled()
    {
        return user.isEnabled();
    }
}
