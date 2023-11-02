package com.foodhub.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtUtils {
    @Value("${token.signing.key}")
    private static String jwtSigningKey;
    private static String extractUserName(String token){
        Claims claims =extractAllClaims(token);
        return claims.getSubject();
    }

    public static String generateJwtToken(UserDetails userDetails){
        return Jwts
                .builder()
                //.setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey)), SignatureAlgorithm.HS512)
                .compact();
    }

    public static boolean isJwtTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private static Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSigningKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
