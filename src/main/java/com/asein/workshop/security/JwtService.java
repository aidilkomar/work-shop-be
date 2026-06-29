package com.asein.workshop.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.asein.workshop.user.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Duration expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException s) {
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Long getId(String token) {
        String id = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);

        return Long.parseLong(id);
    }

    public UUID getUserUuid(String token) {
        String uid = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("uid", String.class);

        return UUID.fromString(uid);
    }

    public List<SimpleGrantedAuthority> getRoles(String token) {
        @SuppressWarnings("unchecked")
        List<String> roles = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", List.class);

        return roles == null ? List.of() : roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        @SuppressWarnings("unchecked")
        List<String> perms = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("perms", List.class);

        return perms == null ? List.of() : perms.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
