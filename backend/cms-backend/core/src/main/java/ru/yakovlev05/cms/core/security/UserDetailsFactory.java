package ru.yakovlev05.cms.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.yakovlev05.cms.core.entity.UserPermission;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsFactory {
    private UserDetailsFactory() {
    }

    public static UserDetailsImpl create(String subject, List<UserRole> roles, List<UserPermission> permissions) {
        return new UserDetailsImpl(
                Long.parseLong(subject),
                new HashSet<>(roles),
                new HashSet<>(permissions),
                getAuthorities(roles, permissions)
        );
    }

    private static List<GrantedAuthority> getRolesGrantedAuthorities(List<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    private static List<GrantedAuthority> getPermissionsGrantedAuthorities(List<UserPermission> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
    }

    private static List<GrantedAuthority> getAuthorities(List<UserRole> roles, List<UserPermission> permissions) {
        var rolesAuthorities = getRolesGrantedAuthorities(roles);
        var permissionsAuthorities = getPermissionsGrantedAuthorities(permissions);

        rolesAuthorities.addAll(permissionsAuthorities);
        return rolesAuthorities;
    }
}
