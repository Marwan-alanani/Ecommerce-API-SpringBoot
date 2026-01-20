package com.marwan.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter
{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        if (request.getHeader("Authorization") == null) {
            // if no authorization header skip logic
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");
        if (!token.startsWith("Bearer ")) {
            throw new ServletException("Invalid token");
        }
        token = token.substring(7);
        if (!jwtService.isTokenValid(token)) {
            throw new ServletException("Invalid token");
        }
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(jwtService.extractEmail(token));


        // set auth
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                ));
        filterChain.doFilter(request, response);

    }
}
