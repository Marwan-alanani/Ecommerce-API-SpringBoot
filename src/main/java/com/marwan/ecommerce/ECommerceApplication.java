package com.marwan.ecommerce;

import com.marwan.ecommerce.model.entity.User;
import com.marwan.ecommerce.model.enums.UserRole;
import com.marwan.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ECommerceApplication
{

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    static void main(String[] args)
    {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    // this is for data seeding
    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        return args -> {
            if (!userRepository.existsByEmail(adminEmail)) {
                User user = User.create(
                        "admin",
                        "admin",
                        UserRole.ADMIN,
                        adminEmail,
                        passwordEncoder.encode(adminPassword)
                );
                userRepository.save(user);
            }
        };
    }
}
