package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.exception.BadRequestException;
import ru.yakovlev05.cms.auth.repository.UserRepository;
import ru.yakovlev05.cms.auth.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User with userId " + userId + " not found"));
    }

    @Override
    public void create(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with username " + user.getUsername() + " or email "
                    + user.getEmail() + " already exists");
        }

        userRepository.save(user);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByUsernameOrEmail(login, login)
                .orElseThrow(() -> new BadRequestException("User with login " + login + " not found"));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
