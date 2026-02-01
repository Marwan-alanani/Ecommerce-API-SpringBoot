package com.marwan.ecommerce.config;

import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                        .requestMatchers("/purchase/**").hasRole(UserRole.ADMIN.name())
                        .requestMatchers("/webhook/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(e -> {
                    e.authenticationEntryPoint((req, resp, ex) -> resp.sendError(401));
                    e.accessDeniedHandler((req, resp, ex) -> resp.sendError(403));
                });

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
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
