package ru.yakovlev05.cms.catalog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.props.JwtProperties;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

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

    private String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Object extractRoles(String token) {
        return extractAllClaims(token).get("roles");
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
            return false;
        }
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, "access_token");
    }

    public Authentication getAuthentication(String token) {
        Object u = extractRoles(token);
        UserDetails userDetails = new JwtUserDetails(
                Long.parseLong(extractSubject(token)),
                null,
                null
        );
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
