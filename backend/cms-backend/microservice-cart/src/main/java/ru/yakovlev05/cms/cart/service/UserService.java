package ru.yakovlev05.cms.cart.service;

import ru.yakovlev05.cms.cart.entity.User;

public interface UserService {
    User getUser(String id);

    void create(User user);

    void deleteById(String id);
}
