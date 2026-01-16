package com.marwan.ecommerce;

import com.marwan.ecommerce.domain.user.entity.User;
import com.marwan.ecommerce.domain.user.enums.UserRole;
import com.marwan.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    // this is for data seeding
    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(User.Create(
                    "Marwan",
                    "Walid",
                    UserRole.Admin,
                    "marwan@mail.com",
                    passwordEncoder.encode("password"))
            );

            userRepository.save(User.Create(
                    "Mazen",
                    "Walid",
                    UserRole.Customer,
                    "mazen@mail.com",
                    passwordEncoder.encode("password"))
            );
            userRepository.save(User.Create(
                    "Malik",
                    "Walid",
                    UserRole.Customer,
                    "malik@mail.com",
                    passwordEncoder.encode("password"))
            );
        };
    }

}
