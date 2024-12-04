package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.entity.User;

public interface UserService {
    User getById(String userId);

    void create(User user);

    User getByLogin(String login);

    void update(User user);

    void deleteById(String userId);
}
