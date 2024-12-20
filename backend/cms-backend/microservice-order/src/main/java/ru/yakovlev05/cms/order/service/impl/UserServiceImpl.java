package ru.yakovlev05.cms.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.order.entity.User;
import ru.yakovlev05.cms.order.exception.BadRequestException;
import ru.yakovlev05.cms.order.repository.UserRepository;
import ru.yakovlev05.cms.order.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User with id " + id + " not found"));
    }
}
