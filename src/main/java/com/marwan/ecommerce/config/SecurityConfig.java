package com.marwan.ecommerce.config;

import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig
{
    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
    {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()  // allow Swagger
                        .requestMatchers(
                                "/auth/**",
                                "/login"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/categories",
                                "/categories/**").permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/categories/**").hasRole(UserRole.ADMIN.name())

                        .requestMatchers(
                                HttpMethod.GET,
                                "/products",
                                "/products/**").permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/products/**").hasRole(UserRole.ADMIN.name())

                        .requestMatchers("/suppliers").hasRole(UserRole.ADMIN.name())

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );       // optional for
        // POST testing in Swagger

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
