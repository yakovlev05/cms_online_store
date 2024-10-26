package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.repository.UserRepository;
import ru.yakovlev05.cms.auth.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("TEST TEST TEST"));
    }

    @Override
    public void create(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        userRepository.save(user);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByUsernameOrEmail(login, login)
                .orElseThrow(() -> new RuntimeException("TEST TEST TEST"));
    }
}
