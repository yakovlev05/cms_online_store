package ru.yakovlev05.cms.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JwtUserDetailsByUserIdService {
    UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException;
}
