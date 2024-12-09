package ru.yakovlev05.cms.auth.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.yakovlev05.cms.core.entity.UserPermission;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Используется только для аутентификации
 */
@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

    private final String id;
    private final String username; // номер телефона
    private final String password;
    private final boolean isConfirmed;
    private final Set<UserRole> roles;
    private final Set<UserPermission> permissions;
    private final List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
