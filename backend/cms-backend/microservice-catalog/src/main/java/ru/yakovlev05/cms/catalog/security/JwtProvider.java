package ru.yakovlev05.cms.catalog.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.catalog.props.JwtProperties;
import ru.yakovlev05.cms.core.entity.UserRole;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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

    private List<UserRole> extractRoles(String token) {
        // (List<UserRole>) extractAllClaims(token).get("roles"); предупреждение при таком подходе
        ObjectMapper objectMapper = new ObjectMapper();
        Object roles = extractAllClaims(token).get("roles");

        return objectMapper.convertValue(roles, new TypeReference<>() {
        });
    }

    public boolean validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return !claims.getExpiration().before(new Date())
                    && claims.get(TOKEN_TYPE).equals("access_token");
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = JwtUserDetailsFactory.create(extractSubject(token), extractRoles(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
