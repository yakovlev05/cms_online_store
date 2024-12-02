package ru.yakovlev05.cms.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.core.entity.UserPermission;
import ru.yakovlev05.cms.core.entity.UserRole;
import ru.yakovlev05.cms.core.props.JwtProperties;
import ru.yakovlev05.cms.core.security.TokenType;
import ru.yakovlev05.cms.core.security.UserDetailsFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Утилитарный класс. Содержит общие методы по работе с jwt токенами.
 * Реализует логику получения данных о пользователе токена (Без запроса к бд),
 * генерации, валидации.
 * Общий класс, используемый другими микросервисами.
 * Логика получения пользователя из бд реализована в сервисе авторизации
 */
@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private static final String TOKEN_TYPE = "token_type";

    private SecretKey getSignKey() {
        String encodedKey = Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(encodedKey.getBytes(), "HmacSHA256");
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    private List<UserRole> extractRoles(String token) {
        // (List<UserRole>) extractAllClaims(token).get("roles"); предупреждение при таком подходе
        ObjectMapper objectMapper = new ObjectMapper();
        Object roles = extractAllClaims(token).get("roles");

        return objectMapper.convertValue(roles, new TypeReference<>() {
        });
    }

    private List<UserPermission> extractPermissions(String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object permissions = extractAllClaims(token).get("permissions");
        return objectMapper.convertValue(permissions, new TypeReference<>() {
        });
    }

    private String createToken(Map<String, Object> claims, String userId, long validityInMs) {
        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validityInMs))
                .signWith(getSignKey())
                .compact();
    }

    public String generateAccessToken(String userId, Set<UserRole> roles, Set<UserPermission> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("permissions", permissions);

        claims.put(TOKEN_TYPE, TokenType.ACCESS_TOKEN);
        return createToken(claims, String.valueOf(userId), jwtProperties.getAccessTokenValidityInMs());
    }

    public String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, TokenType.REFRESH_TOKEN);
        return createToken(claims, String.valueOf(userId), jwtProperties.getRefreshTokenValidityInMs());
    }


    public boolean validateToken(String token, TokenType tokenType) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date())
                    && claims.get(TOKEN_TYPE).equals(tokenType.name());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получает информацию о пользователе, которая есть в токене. Не обращается к бд.
     *
     * @param token jwt токен
     * @return UserDetails
     */
    public Authentication getAuthenticationOffline(String token) {
        UserDetails userDetails = UserDetailsFactory.create(
                extractSubject(token),
                extractRoles(token),
                extractPermissions(token)
        );

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public long getAccessTokenValidityInMs() {
        return jwtProperties.getAccessTokenValidityInMs();
    }

    public long getRefreshTokenValidityInMs() {
        return jwtProperties.getRefreshTokenValidityInMs();
    }
}
