package ru.yakovlev05.cms.user.microserviceuser.service;


import ru.yakovlev05.cms.user.microserviceuser.entity.User;

public interface UserService {
    void create(User user);

    User getById(long userId);

    void update(User user);
}
