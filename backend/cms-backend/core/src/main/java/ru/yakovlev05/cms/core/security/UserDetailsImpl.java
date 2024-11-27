package ru.yakovlev05.cms.core.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.yakovlev05.cms.core.entity.UserPermission;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final long id;
    private final Set<UserRole> roles;
    private final Set<UserPermission> permissions;
    private final List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
