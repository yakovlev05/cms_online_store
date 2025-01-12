package ru.yakovlev05.cms.user.microserviceuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;
import ru.yakovlev05.cms.user.microserviceuser.dto.RequestUserDto;
import ru.yakovlev05.cms.user.microserviceuser.dto.ResponseUserDto;
import ru.yakovlev05.cms.user.microserviceuser.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{user-id}")
    public ResponseUserDto getUser(@PathVariable(name = "user-id") String userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public List<ResponseUserDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return userService.getAllUsers(page, limit);
    }

    @PostMapping
    public ResponseUserDto createAdminUser(@RequestBody RequestUserDto requestUserDto) {
        return userService.addAdminUser(requestUserDto);
    }

    @PutMapping("/{user-id}")
    public ResponseUserDto updateUser(
            @PathVariable(name = "user-id") String userId,
            @RequestBody RequestUserDto requestUserDto) {
        return userService.updateUser(userId, requestUserDto);
    }

    @DeleteMapping("/{user-id}")
    public void deleteUser(@PathVariable(name = "user-id") String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/me")
    public ResponseUserDto getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUser(userDetails.getId());
    }
}
