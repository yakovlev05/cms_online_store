package ru.yakovlev05.cms.user.microserviceuser.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;
import ru.yakovlev05.cms.user.microserviceuser.exception.BadRequestException;
import ru.yakovlev05.cms.user.microserviceuser.repository.UserRepository;
import ru.yakovlev05.cms.user.microserviceuser.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new BadRequestException("User with phone number " + user.getPhoneNumber() + " already exists");
        }

        userRepository.save(user);
    }

    @Override
    public User getById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User with userId " + userId + " not found"));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
