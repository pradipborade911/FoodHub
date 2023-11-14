package com.foodhub.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Optional;

public class JwtUtils {
    private static final String COOKIE_NAME = "token";

    //@Value("${token.signing.key}")
    private static final String jwtSigningKey = "SBVSv7+ul1J2SbTaeKHHELUQvQOxP/aq5XbOpwtQJCWKRqPF9Yr0L1QLHAfmQ0N0SBVSv7+ul1J2SbTaeKHHELUQvQOxP/aq5XbOpwtQJCWKRqPF9Yr0L1QLHAfmQ0N0";
    public static String extractUserName(String token){
        Claims claims =extractAllClaims(token);
        return claims.getSubject();
    }

    static Optional<String> getTokenFromCookie(HttpServletRequest request) {
        // Get the cookies from the request
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        // Find the cookie with the cookie name for the JWT token
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName());
            if (!cookie.getName().equals(COOKIE_NAME)) {
                continue;
            }
            // If we find the JWT cookie, return its value
            return Optional.of(cookie.getValue());
        }
        // Return empty if no cookie is found
        return Optional.empty();
    }

    public static String generateJwtToken(UserDetails userDetails){
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
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
