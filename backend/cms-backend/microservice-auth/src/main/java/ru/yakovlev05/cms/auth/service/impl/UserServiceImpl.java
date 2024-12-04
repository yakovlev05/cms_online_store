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
    public User getById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User with userId " + userId + " not found"));
    }

    @Override
    public void create(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new BadRequestException("User with phone number " + user.getPhoneNumber() + " already exists");
        }

        userRepository.save(user);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByPhoneNumber(login)
                .orElseThrow(() -> new BadRequestException("User with login " + login + " not found"));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(String userId) {
        User user = getById(userId);

        userRepository.delete(user);
    }
}
