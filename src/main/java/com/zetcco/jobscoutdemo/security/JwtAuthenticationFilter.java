package com.zetcco.jobscoutdemo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zetcco.jobscoutdemo.domain.support.User;
import com.zetcco.jobscoutdemo.services.UserService;
import com.zetcco.jobscoutdemo.services.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorization_header = request.getHeader("Authorization");
        if (authorization_header == null || !authorization_header.startsWith("Bearer ")) {
            System.out.println("Unauthorized request, Forwarding for more filtration (could be Register/Login request)");
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt_token = authorization_header.substring(7);
        final String jwt_email = jwtService.getUserEmail(jwt_token);

        if (jwt_email != null && (SecurityContextHolder.getContext().getAuthentication() == null)) {
            User user = userService.loadUserByEmail(jwt_email);
            if (jwtService.isTokenValid(jwt_token, user)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
            }
        }
        filterChain.doFilter(request, response);
    }
    
}