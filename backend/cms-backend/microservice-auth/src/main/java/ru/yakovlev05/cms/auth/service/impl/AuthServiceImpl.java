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
import ru.yakovlev05.cms.auth.security.UserDetailsImpl;
import ru.yakovlev05.cms.auth.security.UserDetailsProvider;
import ru.yakovlev05.cms.auth.service.AuthService;
import ru.yakovlev05.cms.auth.service.KafkaService;
import ru.yakovlev05.cms.auth.service.RoleService;
import ru.yakovlev05.cms.auth.service.UserService;
import ru.yakovlev05.cms.core.entity.UserRole;
import ru.yakovlev05.cms.core.security.TokenType;
import ru.yakovlev05.cms.core.util.JwtUtil;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;

    private final KafkaService kafkaService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsProvider userDetailsProvider;
    private final JwtUtil jwtUtil;

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

        kafkaService.sendUserCreatedEvent(user.getId(), request);

        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public JwtResponseDto login(UserDto request) {
        log.info("Login request received, login: {}", request.getPhoneNumber());
        var t = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );
        log.info("Authentication successful, login: {}", request.getPhoneNumber());

        UserDetailsImpl userDetails = (UserDetailsImpl) t.getPrincipal();

        return fillJwtResponseDto(userDetails);
    }

    @Override
    public JwtResponseDto refresh(JwtRefreshRequestDto request) {
        log.info("Refresh request received, refreshToken:  {}", request.refreshToken());
        if (!jwtUtil.validateToken(request.refreshToken(), TokenType.REFRESH_TOKEN)) {
            throw new BadRequestException("Invalid refresh token");
        }
        log.info("Refresh token validated successful, refreshToken: {}", request.refreshToken());

        Authentication authentication = userDetailsProvider.getAuthentication(request.refreshToken());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return fillJwtResponseDto(userDetails);
    }

    private JwtResponseDto fillJwtResponseDto(UserDetailsImpl userDetails) {
        return new JwtResponseDto(
                jwtUtil.generateAccessToken(
                        userDetails.getId(),
                        userDetails.getRoles(),
                        userDetails.getPermissions()
                ),
                jwtUtil.generateRefreshToken(userDetails.getId()),
                System.currentTimeMillis() + jwtUtil.getAccessTokenValidityInMs(),
                System.currentTimeMillis() + jwtUtil.getRefreshTokenValidityInMs());
    }
}
