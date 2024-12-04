package ru.yakovlev05.cms.user.microserviceuser.service;


import ru.yakovlev05.cms.user.microserviceuser.dto.RequestUserDto;
import ru.yakovlev05.cms.user.microserviceuser.dto.ResponseUserDto;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);

    ResponseUserDto getUser(String userId);

    List<ResponseUserDto> getAllUsers(int page, int limit);

    ResponseUserDto addAdminUser(RequestUserDto requestUserDto);

    ResponseUserDto updateUser(String userId, RequestUserDto requestUserDto);

    void deleteUser(String userId);
}
