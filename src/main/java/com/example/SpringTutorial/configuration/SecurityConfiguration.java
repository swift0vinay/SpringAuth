package com.example.SpringTutorial.configuration;

import com.example.SpringTutorial.configuration.filter.JwtAuthenticationFilter;
import com.example.SpringTutorial.configuration.filter.TestFilter;
import com.example.SpringTutorial.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    AuthenticationProvider authenticationProvider;
    
    @Autowired
    TestFilter testFilter;
    
    @Autowired
    JwtAuthenticationFilter jwtFilter;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(testFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080"));
        corsConfiguration.setAllowedOrigins(List.of(HttpMethod.GET.name(), HttpMethod.POST.name()));
        corsConfiguration.setAllowedHeaders(List.of(Constants.AUTHORIZATION, Constants.CONTENT_TYPE));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("**", corsConfiguration);
        
        return source;
    }
    
}
