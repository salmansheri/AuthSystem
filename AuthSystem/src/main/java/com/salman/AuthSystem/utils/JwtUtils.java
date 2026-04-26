package com.salman.AuthSystem.utils;

import com.salman.AuthSystem.models.Role;
import com.salman.AuthSystem.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



/**
 * This class provides the utility methods for Jwt operations
 *
 * @author Salman Sheriff
 *
 */
@Service
@Getter
@Setter
public class JwtUtils {

    private final SecretKey key;

    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    /**
     * Constructor for JwtUtils
     * @param secret secret key
     * @param accessTtlSeconds access token expiry
     * @param refreshTtlSeconds refresh token expiry
     * @param issuer issuer
     */
    public JwtUtils(@Value("${security.jwt.secret}") String secret, @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,@Value("${security.jwt.refresh-ttl-seconds}")  long refreshTtlSeconds,@Value("${security.jwt.issuer}")  String issuer) {
//        this.key = key;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;

    }

    /**
     *
     * @param user the logged-in user
     * @return the access token
     */
    public String generateAccessToken(User user) {

        Instant now = Instant.now();

        List<String> roles = user.getRoles() == null ? List.of(): user.getRoles().stream().map(Role::getName).toList();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getUserId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claims(Map.of("email", user.getEmail(), "roles", roles, "type", "access"))
                .signWith(key)
                .compact();

    }

    /**
     * <p>This  method generate the refresh token</p>
     * @param user logged-in user
     * @param jti jwt id
     * @return the refresh token
     */

    public String generateRefreshToken(User user, String jti) {

        Instant now = Instant.now();

        List<String> roles = user.getRoles() == null ? List.of(): user.getRoles().stream().map(Role::getName).toList();

        return Jwts.builder()
                .id(jti)
                .subject(user.getUserId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("type", "refresh")
                .signWith(key)
                .compact();

    }


    /**
     * Method used to parse the token
     * @param token accessToken or Refresh Token
     * @return the Jwt<Claims>
     */
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

    }


    /**
     * Method used to parse and check the token is access token or not
     * @param token the access Token
     * @return if access token true else false;
     */
    public boolean isAccessToken(String token) {
        Claims claims = parseToken(token).getPayload();

        return "access".equals(claims.get("type"));
    }


    /**
     * Method used to parse and check the token is refresh token or not
     * @param token the refresh token
     * @return if refresh token true else false
     */
    public boolean isRefreshToken(String token) {
        Claims claims = parseToken(token).getPayload();

        return "refresh".equals(claims.get("type"));
    }

    /**
     * Method to get the user id from the access token or refresh token;
     * @param  token access token to refresh token;
     * @return userId
     */
    public UUID getUserId(String token) {
        Claims claims = parseToken(token).getPayload();
        return UUID.fromString(claims.getSubject());
    }

    /**
     * Method the get the Jwt id
     * @param token access token or refresh token
     * @return Jwt id
     */
    public String getJti(String token) {
        return parseToken(token).getPayload().getId();

    }

    public List<String> getRoles(String token) {
        Claims claims = parseToken(token).getPayload();
        return (List<String>)  claims.get("roles");
    }

    public String getEmail(String token) {
        Claims claims = parseToken(token).getPayload();
        return (String) claims.get("email");

    }





}
