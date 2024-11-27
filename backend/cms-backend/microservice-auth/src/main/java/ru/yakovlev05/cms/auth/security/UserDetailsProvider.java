package ru.yakovlev05.cms.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.util.JwtUtil;

@RequiredArgsConstructor
@Component
public class UserDetailsProvider {

    private final JwtUserDetailsByUserIdService userDetailsByUserIdService;

    private final JwtUtil jwtUtil;

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsByUserIdService.loadUserByUserId(jwtUtil.extractSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
