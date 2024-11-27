package ru.yakovlev05.cms.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.service.UserService;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getByLogin(login);

        return UserDetailsFactory.create(user);
    }
}
