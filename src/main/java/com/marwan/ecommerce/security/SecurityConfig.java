package com.marwan.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
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
                                "/categories/**").hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/products",
                                "/products/**").permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/products/**").hasRole("ADMIN")

                        .requestMatchers("/suppliers").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                //                .httpBasic(Customizer.withDefaults()) // optional for testing
                .addFilterBefore(new JwtFilter(jwtService, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
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
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
