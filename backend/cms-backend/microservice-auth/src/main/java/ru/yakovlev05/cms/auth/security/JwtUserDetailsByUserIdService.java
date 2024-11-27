package ru.yakovlev05.cms.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.service.UserService;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsByUserIdService {

    private final UserService userService;

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userService.getById(Long.parseLong(userId));

        return UserDetailsFactory.create(user);
    }
}
