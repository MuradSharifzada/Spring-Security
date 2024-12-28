package com.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {

    private final String secretKey;

    public JwtService() {
        secretKey = generateSecretKey();
        log.info("Secret key generated successfully.");
    }

    private String generateSecretKey() {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            log.debug("Generating secret key using HS256 algorithm.");
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            log.error("Error generating secret key: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating secret key: " + e.getMessage(), e);
        }
    }

    public String generateToken(String username) {
        log.info("Generating token for username: {}", username);
        Map<String, Object> claims = new HashMap<>();
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            log.debug("Token generated successfully for username: {}", username);
            return token;
        } catch (Exception e) {
            log.error("Error generating token for username {}: {}", username, e.getMessage(), e);
            throw new RuntimeException("Error generating token: " + e.getMessage(), e);
        }
    }

    private Key getSignInKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            log.debug("Decoded secret key for signing.");
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            log.error("Error decoding secret key: {}", e.getMessage(), e);
            throw new RuntimeException("Error decoding secret key: " + e.getMessage(), e);
        }
    }

    public String extractUserName(String token) {
        log.info("Extracting username from token.");
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            log.debug("Parsing claims from token.");
            return Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error extracting claims from token: {}", e.getMessage(), e);
            throw new RuntimeException("Error extracting claims: " + e.getMessage(), e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        boolean isValid = (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        log.info("Token validation for username {}: {}", userDetails.getUsername(), isValid);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        boolean expired = extractExpiration(token).before(new Date());
        log.debug("Token expired status: {}", expired);
        return expired;
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
