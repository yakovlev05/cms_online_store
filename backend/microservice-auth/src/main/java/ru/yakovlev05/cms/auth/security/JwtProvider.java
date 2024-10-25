package ru.yakovlev05.cms.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessTokenValidityInMs}")
    private long accessTokenValidityInMs;

    @Value("${jwt.refreshTokenValidityInMs}")
    private long refreshTokenValidityInMs;

    private final JwtUserDetailsByUserIdService userDetailsByUserIdService;

    private SecretKey getSignKey() {
        String encodedKey = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
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

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            throw new RuntimeException("JWT TOKEN EXPIRED");
        }
    }

    public String generateToken(String userId, Map<String, Object> claims) {
        return createToken(claims, userId, accessTokenValidityInMs);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsByUserIdService.loadUserByUserId(extractSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
