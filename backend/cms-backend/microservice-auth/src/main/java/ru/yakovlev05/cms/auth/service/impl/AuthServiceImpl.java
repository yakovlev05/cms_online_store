package ru.yakovlev05.cms.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.JwtRefreshRequestDto;
import ru.yakovlev05.cms.auth.dto.JwtResponseDto;
import ru.yakovlev05.cms.auth.dto.UserDto;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.exception.BadRequestException;
import ru.yakovlev05.cms.auth.security.JwtProvider;
import ru.yakovlev05.cms.auth.security.JwtUserDetails;
import ru.yakovlev05.cms.auth.service.AuthService;
import ru.yakovlev05.cms.auth.service.KafkaService;
import ru.yakovlev05.cms.auth.service.RoleService;
import ru.yakovlev05.cms.auth.service.UserService;
import ru.yakovlev05.cms.core.entity.UserRole;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;

    private final KafkaService kafkaService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public ResponseEntity<String> registration(UserDto request) {
        log.info("Registration request received, phone number: {}", request.getPhoneNumber());
        User user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userService.create(user);

        roleService.assignRoleToUser(user, UserRole.ROLE_CUSTOMER);

        log.info("Registration successful, phone number: {}", request.getPhoneNumber());

        kafkaService.sendUserCreatedEvent(user.getId(), request, Set.of(UserRole.ROLE_CUSTOMER));

        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public JwtResponseDto login(UserDto request) {
        log.info("Login request received, login: {}", request.getPhoneNumber());
        var t = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );
        log.info("Authentication successful, login: {}", request.getPhoneNumber());

        JwtUserDetails userDetails = (JwtUserDetails) t.getPrincipal();

        return fillJwtResponseDto(userDetails);
    }

    @Override
    public JwtResponseDto refresh(JwtRefreshRequestDto request) {
        log.info("Refresh request received, refreshToken:  {}", request.refreshToken());
        if (!jwtProvider.validateRefreshToken(request.refreshToken())) {
            throw new BadRequestException("Invalid refresh token");
        }
        log.info("Refresh token validated successful, refreshToken: {}", request.refreshToken());

        Authentication authentication = jwtProvider.getAuthentication(request.refreshToken());
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

        return fillJwtResponseDto(userDetails);
    }

    private JwtResponseDto fillJwtResponseDto(JwtUserDetails userDetails) {
        return new JwtResponseDto(
                jwtProvider.generateAccessToken(
                        userDetails.getId(),
                        userDetails.getRoles()
                ),
                jwtProvider.generateRefreshToken(userDetails.getId()),
                System.currentTimeMillis() + jwtProvider.getAccessTokenValidityInMs(),
                System.currentTimeMillis() + jwtProvider.getRefreshTokenValidityInMs());
    }
}
