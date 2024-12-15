package ru.yakovlev05.cms.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.auth.dto.*;
import ru.yakovlev05.cms.auth.entity.Otp;
import ru.yakovlev05.cms.auth.entity.User;
import ru.yakovlev05.cms.auth.exception.BadRequestException;
import ru.yakovlev05.cms.auth.security.UserDetailsImpl;
import ru.yakovlev05.cms.auth.security.UserDetailsProvider;
import ru.yakovlev05.cms.auth.service.*;
import ru.yakovlev05.cms.core.entity.UserRole;
import ru.yakovlev05.cms.core.security.TokenType;
import ru.yakovlev05.cms.core.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final OtpService otpService;

    private final KafkaService kafkaService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsProvider userDetailsProvider;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public ResponseEntity<Object> registration(UserDto request) {
        log.info("Registration request received, phone number: {}", request.getPhoneNumber());
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .isConfirmed(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userService.create(user);

        roleService.assignRoleToUser(user, UserRole.ROLE_CUSTOMER);

        log.info("Registration successful, phone number: {}", request.getPhoneNumber());

        kafkaService.sendUserCreatedEvent(user.getId(), request);

        return ResponseEntity.ok(new AuthMessageDto("Confirmation required"));
    }

    @Override
    public ResponseEntity<Object> login(UserDto request) {
        log.info("Login request received, login: {}", request.getPhoneNumber());
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );

        log.info("Checking phone confirmation status ...");

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        if (!userDetails.isConfirmed()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthMessageDto("Confirmation required"));
        }

        log.info("Authentication successful, login: {}", request.getPhoneNumber());

        return ResponseEntity.ok(fillJwtResponseDto(userDetails));
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

    @Override
    public void confirmPhone(AuthConfirmPhoneDto request) {
        User user = userService.getByPhone(request.getPhoneNumber());
        Otp otp = otpService.getById(request.getOtpId());

        validateOtpRequest(otp);

        if (!user.getPhoneNumber().equals(otp.getDestination())) {
            throw new BadRequestException("OTP does not match");
        }

        otp.setExpired(true);
        otpService.update(otp);

        user.setConfirmed(true);
        user.setUpdatedAt(LocalDateTime.now());
        userService.update(user);

        log.info("Confirm phone successful, phone number: {}", request.getPhoneNumber());
    }

    @Override
    public void resetPassword(AuthResetPasswordRequestDto request) {
        User user = userService.getByPhone(request.getPhoneNumber());
        Otp otp = otpService.getById(request.getOtpId());

        validateOtpRequest(otp);

        if (!user.getPhoneNumber().equals(otp.getDestination())) {
            throw new BadRequestException("OTP does not match");
        }

        otp.setExpired(true);
        otpService.update(otp);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userService.update(user);

        log.info("Reset password successful, phone number: {}", request.getPhoneNumber());
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

    private void validateOtpRequest(Otp otp) {
        if (otp.isExpired()) {
            throw new BadRequestException("Otp is expired");
        }

        if (!otp.isConfirmed()) {
            throw new BadRequestException("Otp is not confirmed");
        }
    }
}
