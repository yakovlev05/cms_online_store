package ru.yakovlev05.cms.user.microserviceuser.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.user.microserviceuser.dto.RequestUserDto;
import ru.yakovlev05.cms.user.microserviceuser.dto.ResponseUserDto;
import ru.yakovlev05.cms.user.microserviceuser.entity.User;
import ru.yakovlev05.cms.user.microserviceuser.exception.BadRequestException;
import ru.yakovlev05.cms.user.microserviceuser.repository.UserRepository;
import ru.yakovlev05.cms.user.microserviceuser.service.AddressService;
import ru.yakovlev05.cms.user.microserviceuser.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressService addressService;

    @Override
    public void create(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new BadRequestException("User with phone number " + user.getPhoneNumber() + " already exists");
        }

        userRepository.save(user);
    }

    @Override
    public ResponseUserDto getUser(long userId) {
        User user = getUserById(userId);

        return fillResponseUserDto(user);
    }

    @Override
    public List<ResponseUserDto> getAllUsers(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<User> users = userRepository.findAll(pageable);

        return users.getContent().stream()
                .map(this::fillResponseUserDto)
                .toList();
    }

    @Override
    public ResponseUserDto addAdminUser(RequestUserDto requestUserDto) {
        User user = User.builder()
                .firstName(requestUserDto.getFistName())
                .lastName(requestUserDto.getLastName())
                .patronymic(requestUserDto.getPatronymic())
                .phoneNumber(requestUserDto.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        this.create(user);

        return fillResponseUserDto(user);
    }

    @Override
    public ResponseUserDto updateUser(long userId, RequestUserDto requestUserDto) {
        User user = getUserById(userId);

        user.setFirstName(requestUserDto.getFistName());
        user.setLastName(requestUserDto.getLastName());
        user.setPatronymic(requestUserDto.getPatronymic());
        user.setPhoneNumber(requestUserDto.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return fillResponseUserDto(user);
    }

    @Override
    public void deleteUser(long userId) {
        User user = getUserById(userId);

        userRepository.delete(user);
    }

    private ResponseUserDto fillResponseUserDto(User user) {
        return new ResponseUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getPhoneNumber(),
                addressService.getString(user.getAddress())
        );
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new BadRequestException("User with id " + userId + " does not exist"));
    }
}
