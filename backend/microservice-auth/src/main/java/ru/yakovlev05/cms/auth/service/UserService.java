package ru.yakovlev05.cms.auth.service;

import ru.yakovlev05.cms.auth.entity.User;

public interface UserService {
    User getById(long userId);
}
