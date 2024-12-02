package ru.yakovlev05.cms.cart.service;

import ru.yakovlev05.cms.cart.entity.User;

public interface UserService {
    User getUser(long id);

    void create(User user);
}
