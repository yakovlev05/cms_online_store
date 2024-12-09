package ru.yakovlev05.cms.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.yakovlev05.cms.auth.entity.Permission;
import ru.yakovlev05.cms.auth.entity.Role;
import ru.yakovlev05.cms.auth.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsFactory {

    private UserDetailsFactory(){}

    public static UserDetailsImpl create(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.isConfirmed(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                user.getPermissions().stream()
                        .map(Permission::getName)
                        .collect(Collectors.toSet()),
                getAuthorities(user)
        );
    }

    private static List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(user.getRoles().stream()
                .map(x -> new SimpleGrantedAuthority(x.getName().name()))
                .toList());

        authorities.addAll(user.getPermissions().stream()
                .map(x -> new SimpleGrantedAuthority(x.getName().name()))
                .toList());

        return authorities;
    }
}
