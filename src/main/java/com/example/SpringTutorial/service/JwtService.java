package com.example.SpringTutorial.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        System.out.println("Claims found are " + claims.toString());
        return claimsResolver.apply(claims);
    }
    
    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public long getExpirationTime() {
        return jwtExpiration;
    }
    
    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails,
                              long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    
    public Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
}
