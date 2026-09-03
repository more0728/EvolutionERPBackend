package com.example.evolutionerp.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//Clase 1
@Component
public class JwtTokenUtil{


    private static final long TOKEN_VALIDITY = 5 * 60 * 60 * 1000; // 5 horas

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(
            String token,
            Function<Claims, T> resolver
    ) {
        return resolver.apply(getAllClaims(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isExpired(String token) {
        return getExpirationDate(token)
                .before(new Date());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        claims.put(
                "roles",
                userDetails.getAuthorities()
                        .stream()
                        .map(auth -> auth.getAuthority())
                        .collect(Collectors.joining(","))
        );

        return createToken(
                claims,
                userDetails.getUsername()
        );
    }

    private String createToken(
            Map<String, Object> claims,
            String username
    ) {

        Date now = new Date();
        Date expiration =
                new Date(now.getTime() + TOKEN_VALIDITY);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(
                        new SecretKeySpec(
                                Base64.getDecoder().decode(secret),
                                SignatureAlgorithm.HS512.getJcaName()
                        )
                )
                .compact();
    }

    public boolean validateToken(
            String token,
            UserDetails userDetails
    ) {
        return getUsernameFromToken(token)
                .equals(userDetails.getUsername())
                && !isExpired(token);
    }
}