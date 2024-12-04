package ru.yakovlev05.cms.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.cart.entity.User;
import ru.yakovlev05.cms.cart.exception.BadRequestException;
import ru.yakovlev05.cms.cart.repository.UserRepository;
import ru.yakovlev05.cms.cart.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("User with id " + id + " not found"));
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        User user = getUser(id);
        userRepository.delete(user);
    }
}
