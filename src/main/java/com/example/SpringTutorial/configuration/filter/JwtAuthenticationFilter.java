package com.example.SpringTutorial.configuration.filter;

import com.example.SpringTutorial.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    JwtService jwtService;
    
    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Calling JWT filter method");
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            System.out.println("Moving forward....");
            return;
        }
        
        try {
            final String jwtToken = authHeader.substring(7);
            final String username = jwtService.getUsername(jwtToken);
            System.out.println("Token find is " + jwtToken);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                
                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("Invalid token");
                }
                
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            System.out.println("Some exception....." + e);
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
    
}
