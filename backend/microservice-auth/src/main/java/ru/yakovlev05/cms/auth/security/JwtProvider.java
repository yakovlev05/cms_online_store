package ru.yakovlev05.cms.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.auth.entity.UserRole;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final JwtUserDetailsByUserIdService userDetailsByUserIdService;

    private static final String TOKEN_TYPE = "token_type";

    private SecretKey getSignKey() {
        String encodedKey = Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(encodedKey.getBytes(), "HmacSHA256");
    }

    private String createToken(Map<String, Object> claims, String userId, long lifeTimeInMs) {
        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifeTimeInMs))
                .signWith(getSignKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean validateToken(String token, String tokenType) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date())
                    && claims.get(TOKEN_TYPE).equals(tokenType);
        } catch (Exception e) {
            throw new RuntimeException("JWT TOKEN EXPIRED");
        }
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, "access_token");
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, "refresh_token");
    }

    public String generateAccessToken(long userId, Set<UserRole> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        claims.put(TOKEN_TYPE, "access_token");
        return createToken(claims, String.valueOf(userId), jwtProperties.getAccessTokenValidityInMs());
    }

    public String generateRefreshToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, "refresh_token");
        return createToken(claims, String.valueOf(userId), jwtProperties.getRefreshTokenValidityInMs());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsByUserIdService.loadUserByUserId(extractSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public long getAccessTokenValidityInMs() {
        return jwtProperties.getAccessTokenValidityInMs();
    }

    public long getRefreshTokenValidityInMs() {
        return jwtProperties.getRefreshTokenValidityInMs();
    }

}
