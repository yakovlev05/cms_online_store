package ru.yakovlev05.cms.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.JwtRequestDto;
import ru.yakovlev05.cms.auth.dto.JwtResponseDto;
import ru.yakovlev05.cms.auth.dto.UserDto;
import ru.yakovlev05.cms.auth.entity.Role;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.entity.UserRole;
import ru.yakovlev05.cms.auth.security.JwtProvider;
import ru.yakovlev05.cms.auth.security.JwtUserDetails;
import ru.yakovlev05.cms.auth.service.AuthService;
import ru.yakovlev05.cms.auth.service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<String> registration(UserDto request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userService.create(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public JwtResponseDto login(JwtRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );

        JwtUserDetails userDetails = (JwtUserDetails) authentication.getDetails();


        return new JwtResponseDto(
                jwtProvider.generateToken(
                        String.valueOf(userDetails.getId()),
                        new HashMap<>()
                ),
                null);
    }
}
