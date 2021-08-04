package com.systech.mss.seurity;

import com.systech.mss.domain.SecurityConfig;
import com.systech.mss.domain.User;
import com.systech.mss.repository.SecurityConfigRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;

public class TokenProvider {
    @Inject
    private Logger log;
    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    private SecurityConfigRepository securityConfigRepository;


    public String generateToken(User user, boolean rememberMe) {
        if (getSecurityConfig().isPresent()) {
            SecurityConfig securityConfig = getSecurityConfig().get();
            String issuer = securityConfig.getIssuer();
            long issuedTime = System.currentTimeMillis();
            long tokenValidityMillis
                    = 1000 * securityConfig.getTokenValidityMillis();
            long tokenValidityMillisForRememberMe
                    = 1000 * securityConfig.getTokenValidityMillisForRememberMe();
            long expirationTime = issuedTime
                    + (rememberMe ? tokenValidityMillisForRememberMe : tokenValidityMillis);
            Key key = keyGenerator.generateKey();
            return Jwts.builder()
                    .setHeaderParam(TYPE, JWT_TYPE)
                    .setId(UUID.randomUUID().toString())
                    .setSubject(user.getLogin())
                    .setIssuer(issuer)
                    .setIssuedAt(new Date(issuedTime))
                    .setExpiration(new Date(expirationTime))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        }
        log.error("Token generation configs not set");
        return null;


    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(keyGenerator.generateKey()).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return username.equals(user.getLogin()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Optional<SecurityConfig> getSecurityConfig() {
        return securityConfigRepository.findAll().stream()
                .findFirst();
    }

}
