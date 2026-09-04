package com.evolutionerp.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:18000000}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(getAllClaims(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",
                userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(",")));
        return createToken(claims, userDetails.getUsername());
    }

    public String generateTokenWithSociedad(UserDetails userDetails, String codSociedad) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",
                userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(",")));
        claims.put("sociedad", codSociedad);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(exp)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !isExpired(token);
    }

    public String getSociedadFromToken(String token) {
        return getClaim(token, c -> c.get("sociedad", String.class));
    }
}
