package ru.yakovlev05.cms.catalog.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserDetailsFactory {

    private JwtUserDetailsFactory() {
    }

    public static JwtUserDetails create(String subject, List<UserRole> roles) {
        return new JwtUserDetails(
                Long.parseLong(subject),
                new HashSet<>(roles),
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())
        );
    }
}
