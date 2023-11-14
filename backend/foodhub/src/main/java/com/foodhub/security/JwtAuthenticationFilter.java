package com.foodhub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = null;
        try{
            jwtToken = JwtUtils.getTokenFromCookie(request).orElseThrow();
            System.out.println("jwtToken: " + jwtToken);
        }catch (Exception e){
            System.out.println("jwtToken not found!");
            filterChain.doFilter(request, response);
            return;
        }

        String username = JwtUtils.extractUserName(jwtToken);
        if(SecurityContextHolder.getContext().getAuthentication() != null || username == null || username.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(!JwtUtils.isJwtTokenValid(jwtToken, userDetails)){
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken verifiedAuthToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(verifiedAuthToken);
        filterChain.doFilter(request, response);
    }
}
